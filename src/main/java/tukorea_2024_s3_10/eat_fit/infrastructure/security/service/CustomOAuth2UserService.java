package tukorea_2024_s3_10.eat_fit.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.*;
import tukorea_2024_s3_10.eat_fit.user.entity.User;
import tukorea_2024_s3_10.eat_fit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // naver, kakao
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        }

        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();

        User existUser = userRepository.findByUsername(username);

        if (existUser == null) {
            User user = User.builder()
                    .role("ROLE_GUEST")
                    .username(username)
                    .build();
            userRepository.save(user);
            UserDto userDto = new UserDto();
            userDto.setUsername(username);
            userDto.setRole("ROLE_GUEST");
            return new CustomOAuth2User(userDto);
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(existUser.getUsername());
        userDto.setRole(existUser.getRole());
        return new CustomOAuth2User(userDto);
    }
}
