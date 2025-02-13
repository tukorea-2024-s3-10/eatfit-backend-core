package tukorea_2024_s3_10.eat_fit.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.global.security.Encryption;
import tukorea_2024_s3_10.eat_fit.user.dto.request.LoginRequest;
import tukorea_2024_s3_10.eat_fit.user.dto.request.SignupRequest;
import tukorea_2024_s3_10.eat_fit.user.entity.User;
import tukorea_2024_s3_10.eat_fit.user.entity.UserGoal;
import tukorea_2024_s3_10.eat_fit.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.user.exception.EmailAlreadyExistsException;
import tukorea_2024_s3_10.eat_fit.user.repository.UserGoalRepository;
import tukorea_2024_s3_10.eat_fit.user.repository.UserProfileRepository;
import tukorea_2024_s3_10.eat_fit.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.user.util.UserGoalCalculator;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserGoalRepository userGoalRepository;
    private final UserProfileRepository userProfileRepository;

//    @Transactional
//    public void signup(SignupRequest signupRequest) {
//        if (userRepository.existsByEmail(signupRequest.getEmail())) {
//            throw new EmailAlreadyExistsException("이미 가입된 이메일입니다.");
//        }
//
//        String salt = Encryption.getSalt();
//
//        User user = User.builder()
//                .password(Encryption.sha256Encode(signupRequest.getPassword(), salt))
//                .salt(salt)
//                .name(signupRequest.getName())
//                .build();
//
//        UserProfile userProfile = UserProfile.builder()
//                .user(user)
//                .gender(signupRequest.getGender())
//                .birthYear(signupRequest.getBirthYear())
//                .height(signupRequest.getHeight())
//                .weight(signupRequest.getWeight())
//                .goalCategory(signupRequest.getGoalCategory())
//                .targetWeight(signupRequest.getTargetWeight())
//                .disease(signupRequest.getDisease())
//                .build();
//
//        UserGoal userGoal = UserGoalCalculator.recommendUserGoal(userProfile);
//
//        userRepository.save(user);
//        userProfileRepository.save(userProfile);
//        userGoalRepository.save(userGoal);
//    }
//
//    public void login(LoginRequest loginRequest, HttpSession httpSession) {
//        User user = userRepository.findByEmail(loginRequest.getEmail());
//
//        if (user == null) {
//            System.out.println("로그인 실패");
//            return;
//        }
//
//        String salt = user.getSalt();
//
//        if (Encryption.sha256Encode(loginRequest.getPassword(), salt).equals(user.getPassword())) {
//            httpSession.setAttribute("userId", user.getId());
//            return;
//        }
//
//        System.out.println("로그인 실패");
//    }
}
