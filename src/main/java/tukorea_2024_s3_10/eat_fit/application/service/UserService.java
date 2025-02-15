package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.domain.auth.Role;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileInitRequest;
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
    public void initProfile(ProfileInitRequest profileInitRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId).get();

        UserProfile userProfile = UserProfile.builder()
                .name(profileInitRequest.getName())
                .birthYear(profileInitRequest.getBirthYear())
                .targetWeight(profileInitRequest.getTargetWeight())
                .weight(profileInitRequest.getWeight())
                .height(profileInitRequest.getHeight())
                .user(user)
                .build();
        userProfileRepository.save(userProfile);
        user.changeRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public ProfileResponse getProfile() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        UserProfile userProfile = userProfileRepository.findById(currentUserId).get();

        return new ProfileResponse(userProfile);

    }

    public void editProfile(ProfileEditRequest profileEditRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        UserProfile userProfile = userProfileRepository.findById(currentUserId).get();

        userProfile.setName(profileEditRequest.getName());
        userProfile.setGender(profileEditRequest.getGender());
        userProfile.setBirthYear(profileEditRequest.getBirthYear());
        userProfile.setHeight(profileEditRequest.getHeight());
        userProfile.setWeight(profileEditRequest.getWeight());
        userProfile.setGoalCategory(profileEditRequest.getGoalCategory());
        userProfile.setTargetWeight(profileEditRequest.getTargetWeight());
        userProfile.setDisease(profileEditRequest.getDisease());

        userProfileRepository.save(userProfile);


    }
}
