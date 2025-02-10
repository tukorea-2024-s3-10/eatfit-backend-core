package tukorea_2024_s3_10.eat_fit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.user.dto.request.SignupRequest;
import tukorea_2024_s3_10.eat_fit.user.entity.User;
import tukorea_2024_s3_10.eat_fit.user.entity.UserGoal;
import tukorea_2024_s3_10.eat_fit.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.user.exception.EmailAlreadyExistsException;
import tukorea_2024_s3_10.eat_fit.user.repository.UserGoalRepository;
import tukorea_2024_s3_10.eat_fit.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.user.util.UserGoalCalculator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;

    public void signup(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new EmailAlreadyExistsException("이미 가입된 이메일입니다.");
        }

        User user = User.builder()
                .name(signupRequest.getName())
                .password(signupRequest.getPassword())
                .email(signupRequest.getEmail())
                .build();

        UserProfile userProfile = UserProfile.builder()
                .user(user)
                .gender(signupRequest.getGender())
                .birthYear(signupRequest.getBirthYear())
                .height(signupRequest.getHeight())
                .weight(signupRequest.getWeight())
                .goalCategory(signupRequest.getGoalCategory())
                .targetWeight(signupRequest.getTargetWeight())
                .disease(signupRequest.getDisease())
                .build();

        UserGoal userGoal = UserGoalCalculator.recommendUserGoal(userProfile);

        userRepository.save(user);
        userGoalRepository.save(userProfile);
    }
}
