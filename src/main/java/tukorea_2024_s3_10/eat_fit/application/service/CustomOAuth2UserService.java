package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.*;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 제공자 + OAuth2 식별 ID 값으로 우리 서비스 내에서 가입 체크
        // ex) naver_12345678a
        String oAuthId = generateOAuthId(userRequest, oAuth2User);

        // 위 생성된 oAuthId 값으로 기존에 가입된 사용자라면 해당 사용자 반환, 새로운 사용자라면 DB 추가 후 사용자 반환
        User user = findOrCreateUser(oAuthId);

        return new CustomOAuth2User(user.getId(), user.getOauthId(), user.getRole().name());
    }

    private String generateOAuthId(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();

        return switch (provider) {
            case "kakao" -> "kakao_" + oAuth2User.getAttributes().get("id").toString();
            case "naver" -> {
                Object response = oAuth2User.getAttributes().get("response");
                if (response instanceof Map<?, ?> attributes) {
                    yield "naver_" + attributes.get("id").toString();
                }
                yield null;
            }
            default -> null;
        };
    }

    private User findOrCreateUser(String oAuthId) {
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
