package tukorea_2024_s3_10.eat_fit.global.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.global.user.dto.request.SignupRequest;
import tukorea_2024_s3_10.eat_fit.global.user.entity.User;
import tukorea_2024_s3_10.eat_fit.global.user.entity.UserGoal;
import tukorea_2024_s3_10.eat_fit.global.user.repository.UserGoalRepository;
import tukorea_2024_s3_10.eat_fit.global.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.global.user.util.UserGoalCalculator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;

    public void signup(SignupRequest signupRequest) {
        /**
         * (0.) 유효성 검증 < 컨트롤러 레벨에서
         * 1. 이메일 중복 체크
         */

        User user = User.builder()
                .name(signupRequest.getName())
                .password(signupRequest.getPassword())
                .email(signupRequest.getEmail())
                .build();

        UserGoal userGoal = UserGoal.builder()
                .birthYear(signupRequest.getBirthYear())
                .gender(signupRequest.getGender())
                .goal(signupRequest.getGoal())
                .height(signupRequest.getHeight())
                .weight(signupRequest.getWeight())
                .user(user)
                .build();

        userGoal = UserGoalCalculator.recommendUserGoal(userGoal);

        userRepository.save(user);
        userGoalRepository.save(userGoal);
    }
}
