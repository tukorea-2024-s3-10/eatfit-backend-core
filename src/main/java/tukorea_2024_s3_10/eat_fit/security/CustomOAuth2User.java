package tukorea_2024_s3_10.eat_fit.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    @Getter
    private final Long userId;
    private final String oauthId;
    private final String role;

    public CustomOAuth2User(Long userId, String oauthId, String role) {
        this.userId = userId;
        this.oauthId = oauthId;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> role);
    }

    @Override
    public String getName() {
        return oauthId;
    }
}
