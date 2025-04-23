package tukorea_2024_s3_10.eat_fit.infrastructure.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tukorea_2024_s3_10.eat_fit.domain.RefreshEntity;
import tukorea_2024_s3_10.eat_fit.domain.RefreshRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.jwt.JwtUtil;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.CustomOAuth2User;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Value("${client_url}")
    private String clientUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        Long userId = customUserDetails.getUserId();
        String oAuthId = customUserDetails.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String refresh = jwtUtil.createJwt("refresh", oAuthId, userId, role, 86400000L);

        addRefreshEntity(oAuthId, refresh, 86400000L);

        response.addCookie(createCookie("refresh_token", refresh));
        response.setStatus(HttpStatus.OK.value());

        if (role.equals("ROLE_GUEST")) {
            response.sendRedirect(clientUrl + "/profile/setup");
            return;
        }

        response.sendRedirect(clientUrl + "/dashboard");
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setDomain("eatfit.site");

        return cookie;
    }

    private void addRefreshEntity(String oAuthId, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setOauthId(oAuthId);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        refreshRepository.save(refreshEntity);
    }
}