package tukorea_2024_s3_10.eat_fit.security.security;

import org.springframework.security.core.context.SecurityContextHolder;
import tukorea_2024_s3_10.eat_fit.security.CustomOAuth2User;

public class SecurityUtil {
    public static Long getCurrentUserId() {
        return ((CustomOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }
}
