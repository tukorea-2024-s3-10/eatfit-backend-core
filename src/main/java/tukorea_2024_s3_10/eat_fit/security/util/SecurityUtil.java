package tukorea_2024_s3_10.eat_fit.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.CustomOAuth2User;

public class SecurityUtil {

    public static Long getCurrentUserId() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 존재하지 않습니다.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOAuth2User oAuth2User) {
            return oAuth2User.getUserId();
        }

        throw new IllegalStateException("인증 정보를 식별할 수 없습니다.");
    }
}
