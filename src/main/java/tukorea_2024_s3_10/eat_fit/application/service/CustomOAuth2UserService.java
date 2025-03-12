package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.domain.auth.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 제공자 + "_" + 제공자 식별 ID를 조합하여 우리 서비스의 고유 사용자 ID 생성
        // 예시: "naver_123456789a", "kakao_987654321b"
        String oAuthId = getOAuthId(userRequest, oAuth2User);

        User user = findOrCreateUserByOAuthId(oAuthId);
        return new CustomOAuth2User(user.getId(), user.getOauthId(), user.getRole().name());
    }

    private String getOAuthId(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = switch (registrationId) {
            case "naver" -> new NaverResponse(oAuth2User.getAttributes());
            case "kakao" -> new KakaoResponse(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("Unsupported provider: " + registrationId);
        };
        return oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
    }

    private User findOrCreateUserByOAuthId(String oAuthId) {
        return userRepository.findByOauthId(oAuthId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .oauthId(oAuthId)
                            .role(Role.ROLE_GUEST)
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
