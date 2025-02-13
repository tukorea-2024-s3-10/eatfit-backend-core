package tukorea_2024_s3_10.eat_fit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.dto.CustomOAuth2User;
import tukorea_2024_s3_10.eat_fit.user.dto.request.InitProfileRequest;
import tukorea_2024_s3_10.eat_fit.user.entity.User;
import tukorea_2024_s3_10.eat_fit.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.user.repository.UserGoalRepository;
import tukorea_2024_s3_10.eat_fit.user.repository.UserProfileRepository;
import tukorea_2024_s3_10.eat_fit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserProfileRepository userProfileRepository;

    public void initProfile(InitProfileRequest initProfileRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) principal;
        String username = customOAuth2User.getUsername();
        User user = userRepository.findByUsername(customOAuth2User.getUsername());

        UserProfile userProfile = UserProfile.builder()
                .name(initProfileRequest.getName())
                .birthYear(initProfileRequest.getBirthYear())
                .targetWeight(initProfileRequest.getTargetWeight())
                .weight(initProfileRequest.getWeight())
                .height(initProfileRequest.getHeight())
                .user(user)
                .build();
        userProfileRepository.save(userProfile);
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
