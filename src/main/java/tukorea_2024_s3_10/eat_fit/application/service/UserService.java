package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.application.dto.IntakeGoalResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.TodayIntakeResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileSetupRequest;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserIntakeGoalRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.BodyProfileRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static tukorea_2024_s3_10.eat_fit.application.util.UserGoalCalculator.recommendUserGoal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserIntakeGoalRepository userIntakeGoalRepository;
    private final BodyProfileRepository bodyProfileRepository;
    private final DietRecordRepository dietRecordRepository;

    @Transactional
    public void initProfile(ProfileSetupRequest profileSetupRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(currentUserId).get();

        BodyProfile bodyProfile = BodyProfile.builder()
                .targetWeight(profileSetupRequest.getTargetWeight())
                .weight(profileSetupRequest.getWeight())
                .height(profileSetupRequest.getHeight())
                .build();


        // 유저 맞춤 섭취량 설정
        UserIntakeGoal userIntakeGoal = recommendUserGoal(bodyProfile);
        userIntakeGoalRepository.save(userIntakeGoal);

        bodyProfileRepository.save(bodyProfile);
        user.updateRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public ProfileResponse getProfile() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        BodyProfile bodyProfile = bodyProfileRepository.findById(currentUserId).get();

        return new ProfileResponse(bodyProfile);

    }

    @Transactional
    public void editProfile(ProfileEditRequest profileEditRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        BodyProfile bodyProfile = bodyProfileRepository.findById(currentUserId).get();

        bodyProfile.setGender(profileEditRequest.getGender());
        bodyProfile.setHeight(profileEditRequest.getHeight());
        bodyProfile.setWeight(profileEditRequest.getWeight());
        bodyProfile.setTargetWeight(profileEditRequest.getTargetWeight());

        // 유저 맞춤 섭취량 설정
        UserIntakeGoal userIntakeGoal = recommendUserGoal(bodyProfile);
        userIntakeGoalRepository.save(userIntakeGoal);

        bodyProfileRepository.save(bodyProfile);


    }

    public IntakeGoalResponse getIntakeGoal() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findById(currentUserId).get();

        return new IntakeGoalResponse(userIntakeGoal);
    }

    public TodayIntakeResponse calculateTodayIntake() {

        // 현재 사용자와 오늘의 날짜를 바탕으로
        Long currentUserId = SecurityUtil.getCurrentUserId();
        LocalDate today = LocalDate.now();

        // 오늘 먹었던 식단을 조회
        List<DietRecord> todayDietRecords = dietRecordRepository.findByUserIdAndDate(currentUserId, today);

        // 오늘 먹었던 식단들의 영양 성분을 합해 응답 객체 생성
        return new TodayIntakeResponse(todayDietRecords);
    }
}
