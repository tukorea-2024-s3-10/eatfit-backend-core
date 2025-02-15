package tukorea_2024_s3_10.eat_fit.infrastructure.security.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    @Getter
    private final Long userId;
    private final String oAuthId;
    private final String role;

    public CustomOAuth2User(Long userId, String oAuthId, String role) {
        this.userId = userId;
        this.oAuthId = oAuthId;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add((GrantedAuthority) () -> role);
        return authorities;
    }

    @Override
    public String getName() {
        return oAuthId;
    }
}
