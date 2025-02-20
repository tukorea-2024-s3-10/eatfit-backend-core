package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.application.dto.IntakeGoalResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.TodayIntakeResponse;
import tukorea_2024_s3_10.eat_fit.domain.auth.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.security.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileInitRequest;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserIntakeGoalRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserProfileRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static tukorea_2024_s3_10.eat_fit.application.util.UserGoalCalculator.recommendUserGoal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserIntakeGoalRepository userIntakeGoalRepository;
    private final UserProfileRepository userProfileRepository;
    private final DietRecordRepository dietRecordRepository;

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


        // 유저 맞춤 섭취량 설정
        UserIntakeGoal userIntakeGoal = recommendUserGoal(userProfile);
        userIntakeGoalRepository.save(userIntakeGoal);

        userProfileRepository.save(userProfile);
        user.changeRole(Role.ROLE_USER);
        userRepository.save(user);
    }

    public ProfileResponse getProfile() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        UserProfile userProfile = userProfileRepository.findById(currentUserId).get();

        return new ProfileResponse(userProfile);

    }

    @Transactional
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

        // 유저 맞춤 섭취량 설정
        UserIntakeGoal userIntakeGoal = recommendUserGoal(userProfile);
        userIntakeGoalRepository.save(userIntakeGoal);

        userProfileRepository.save(userProfile);


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
