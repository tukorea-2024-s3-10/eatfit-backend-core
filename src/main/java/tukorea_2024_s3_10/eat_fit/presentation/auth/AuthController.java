package tukorea_2024_s3_10.eat_fit.presentation.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.domain.RefreshRepository;
import tukorea_2024_s3_10.eat_fit.security.jwt.JwtUtil;

@Slf4j
@RestController
@RequestMapping("/api/core/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Operation(summary = "access_token 재발급", description = "refresh_token을 이용한 access_token 재발급")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> sendAccessTokenByRefreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken, HttpServletResponse response) {
        // refresh_token이 서버에서 관리 중인 목록에 없는 경우
        if (!refreshRepository.existsByRefresh(refreshToken)) {
            return new ResponseEntity<>("invalid refresh_token", HttpStatus.UNAUTHORIZED);
        }

        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh_token is expired", HttpStatus.UNAUTHORIZED);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        String oAuthId = jwtUtil.getOAuthId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        // Access Token 수명 : 1시간
        String accessToken = jwtUtil.createJwt("access_token", oAuthId, userId, role, 1000 * 60 * 60L);

        response.setHeader("Authorization", "Bearer " + accessToken);

        log.info("액세스 토큰 재발급 성공");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}