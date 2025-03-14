package tukorea_2024_s3_10.eat_fit.infrastructure.security;

import org.springframework.security.core.context.SecurityContextHolder;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.CustomOAuth2User;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) principal).getUserId();
        } else if (principal instanceof String) {
            String user = (String) principal;
            if ("anonymousUser".equals(user)) {
                throw new IllegalStateException("익명 사용자는 접근할 수 없습니다.");
            }
        }

        throw new IllegalStateException("인증된 사용자가 없습니다.");
    }
}
