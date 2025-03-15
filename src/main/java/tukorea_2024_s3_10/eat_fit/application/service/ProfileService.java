package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.BodyProfileRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileSetupRequest;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final BodyProfileRepository bodyProfileRepository;

    @Transactional
    public void setupProfile(ProfileSetupRequest profileSetupRequest) {

        // 현재 로그인 중인 사용자의 식별자를 불러옴
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> new NoSuchElementException("Can't find user"));

        // 이미 초기 프로필 설정을 마친 사용자는 로직 수행이 안되도록
        if (user.getRole().equals(Role.ROLE_USER)) {
            return;
        }

        user.updateNickname(profileSetupRequest.getNickname());
        user.updateRole(Role.ROLE_USER);

        userRepository.save(user);

        BodyProfile bodyProfile = BodyProfile.builder()
                .userId(currentUserId)
                .age(profileSetupRequest.getAge())
                .height(profileSetupRequest.getHeight())
                .gender(profileSetupRequest.getGender())
                .weight(profileSetupRequest.getWeight())
                .targetWeight(profileSetupRequest.getTargetWeight())
                .targetType(profileSetupRequest.getTargetType())
                .build();
        bodyProfileRepository.save(bodyProfile);


    }
}
