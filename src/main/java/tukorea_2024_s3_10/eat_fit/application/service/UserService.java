package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.domain.auth.Role;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.InitProfileRequest;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserGoalRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserProfileRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserProfileRepository userProfileRepository;

    @Transactional
    public void initProfile(InitProfileRequest initProfileRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId).get();

        UserProfile userProfile = UserProfile.builder()
                .name(initProfileRequest.getName())
                .birthYear(initProfileRequest.getBirthYear())
                .targetWeight(initProfileRequest.getTargetWeight())
                .weight(initProfileRequest.getWeight())
                .height(initProfileRequest.getHeight())
                .user(user)
                .build();
        userProfileRepository.save(userProfile);
        user.changeRole(Role.ROLE_USER);
        userRepository.save(user);
    }
}
