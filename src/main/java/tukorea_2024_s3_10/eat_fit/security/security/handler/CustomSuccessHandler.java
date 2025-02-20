package tukorea_2024_s3_10.eat_fit.security.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tukorea_2024_s3_10.eat_fit.domain.RefreshEntity;
import tukorea_2024_s3_10.eat_fit.domain.RefreshRepository;
import tukorea_2024_s3_10.eat_fit.security.jwt.JwtUtil;
import tukorea_2024_s3_10.eat_fit.security.CustomOAuth2User;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customUserDetails.getUserId();
        String oauthId = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // Access Token 수명 : 1시간
        // String access = jwtUtil.createJwt("access", oAuthId, userId, role, 1000 * 60 * 60L);

        // Refresh Token 수명 : 2주
        String refresh = jwtUtil.createJwt("refresh_token", oauthId, userId, role, 1000 * 60 * 60 * 24 * 14L);

        addRefreshEntity(userId, refresh, 1000 * 60 * 60 * 24 * 14L);

        /**
         * Access Token은 헤더로 전송하는데 리다이렉션 방식에서는 클라이언트가 받을 방법이 존재하지 않는다.
         * 따라서 Refresh Token만 발급하고 Access Token은 별도로 받는 방식으로 진행
         */
        // Access Token은 Authorization 헤더에 "Bearer " + Access Token 형태로 전송
        // response.setHeader("Authorization", "Bearer " + access);

        // Refresh Token은 쿠키로 전송
        response.addCookie(createCookie("refresh_token", refresh));

        response.setStatus(HttpStatus.OK.value());

        if (role.equals("ROLE_GUEST")) {
            response.sendRedirect("http://localhost:3000/physical-info");
            return;
        }
        response.sendRedirect("http://localhost:3000");
    }

    private Cookie createCookie(String name, String value) {

        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(Long userId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUserId(userId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}