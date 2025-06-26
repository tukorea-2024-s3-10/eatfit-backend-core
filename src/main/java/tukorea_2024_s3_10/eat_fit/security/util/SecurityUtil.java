package tukorea_2024_s3_10.eat_fit.security.util;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.CustomOAuth2User;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOAuth2User oAuth2User) {
            return oAuth2User.getUserId();
        }

        throw new AccessDeniedException("인증된 사용자의 정보 타입이 올바르지 않습니다.");
    }
}
