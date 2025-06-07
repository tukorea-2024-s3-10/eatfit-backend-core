package tukorea_2024_s3_10.eat_fit.presentation.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.index.qual.EnsuresLTLengthOf;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.domain.RefreshEntity;
import tukorea_2024_s3_10.eat_fit.domain.RefreshRepository;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.infrastructure.jwt.JwtUtil;

import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/api/core/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @PostMapping("/reissue")
    @Operation(summary = "액세스 토큰 재발급", description = "쿠키로 받은 refresh_token을 이용하여 검증 후 access_token 재발급")
    public ResponseEntity<ApiResponse<Void>> reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new IllegalArgumentException("No cookies found in the request");
        }

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> "refresh_token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("refresh_token is empty"));

        if (jwtUtil.isExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("refresh_token is expired"));
        }

        if (!refreshRepository.existsByRefresh(refreshToken)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("refresh_token not found in DB"));
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        String oAuthId = jwtUtil.getOAuthId(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        String newAccessToken = jwtUtil.createJwt("access", oAuthId, userId, role, 6000000L);

        response.setHeader("Authorization", "Bearer " + newAccessToken);

        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null));
    }

}