package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tukorea_2024_s3_10.eat_fit.application.dto.IntakeGoalResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.user.FeedbackResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.user.TodayNutritionResponse;
import tukorea_2024_s3_10.eat_fit.batch.Feedback;
import tukorea_2024_s3_10.eat_fit.batch.FeedbackRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.Role;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.*;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileSetupRequest;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.UserIntakeGoalRequest;

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
    private final FeedbackRepository feedbackRepository;
    private final UserDiseaseRepository userDiseaseRepository;

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
        User user = userRepository.findById(currentUserId).get();
        BodyProfile bodyProfile = bodyProfileRepository.findById(currentUserId).get();
        ProfileResponse profileResponse = new ProfileResponse(bodyProfile);
        profileResponse.setName(user.getNickname());
        profileResponse.setDisease(userDiseaseRepository.findByUserId(currentUserId).getDisease());
        return profileResponse;
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

        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findByUserId(currentUserId).get();

        return new IntakeGoalResponse(userIntakeGoal);
    }

    @Transactional
    public void editIntakeGoal(UserIntakeGoalRequest userIntakeGoalRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findByUserId(currentUserId).get();

        if (userIntakeGoalRequest.getCalorieGoal() != 0 && userIntakeGoalRequest.getCalorieGoal() != userIntakeGoal.getCalorieGoal()) {
            userIntakeGoal.setCalorieGoal(userIntakeGoalRequest.getCalorieGoal());
        }
        if (userIntakeGoalRequest.getSodiumGoal() != 0 && userIntakeGoalRequest.getSodiumGoal() != userIntakeGoal.getSodiumGoal()) {
            userIntakeGoal.setSodiumGoal(userIntakeGoalRequest.getSodiumGoal());
        }
        if (userIntakeGoalRequest.getCarbohydrateGoal() != 0 && userIntakeGoalRequest.getCarbohydrateGoal() != userIntakeGoal.getCarbohydrateGoal()) {
            userIntakeGoal.setCarbohydrateGoal(userIntakeGoalRequest.getCarbohydrateGoal());
        }
        if (userIntakeGoalRequest.getSugarGoal() != 0 && userIntakeGoalRequest.getSugarGoal() != userIntakeGoal.getSugarGoal()) {
            userIntakeGoal.setSugarGoal(userIntakeGoalRequest.getSugarGoal());
        }
        if (userIntakeGoalRequest.getFatGoal() != 0 && userIntakeGoalRequest.getFatGoal() != userIntakeGoal.getFatGoal()) {
            userIntakeGoal.setFatGoal(userIntakeGoalRequest.getFatGoal());
        }
        if (userIntakeGoalRequest.getTransFatGoal() != 0 && userIntakeGoalRequest.getTransFatGoal() != userIntakeGoal.getTransFatGoal()) {
            userIntakeGoal.setTransFatGoal(userIntakeGoalRequest.getTransFatGoal());
        }
        if (userIntakeGoalRequest.getSaturatedFatGoal() != 0 && userIntakeGoalRequest.getSaturatedFatGoal() != userIntakeGoal.getSaturatedFatGoal()) {
            userIntakeGoal.setSaturatedFatGoal(userIntakeGoalRequest.getSaturatedFatGoal());
        }
        if (userIntakeGoalRequest.getProteinGoal() != 0 && userIntakeGoalRequest.getProteinGoal() != userIntakeGoal.getProteinGoal()) {
            userIntakeGoal.setProteinGoal(userIntakeGoalRequest.getProteinGoal());
        }
        if (userIntakeGoalRequest.getCholesterolGoal() != 0 && userIntakeGoalRequest.getCholesterolGoal() != userIntakeGoal.getCholesterolGoal()) {
            userIntakeGoal.setCholesterolGoal(userIntakeGoalRequest.getCholesterolGoal());
        }

        userIntakeGoalRepository.save(userIntakeGoal);

    }

    public TodayNutritionResponse getTodayNutrition() {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        // 현재 사용자가 오늘 날짜에 섭취한 식단 정보를 리스트로 가져온다.
        List<DietRecord> todayDietRecords = dietRecordRepository.findByUserIdAndDate(currentUserId, LocalDate.now());

        int totalCalorie = 0;
        int totalSodiumMg = 0;
        int totalCarbohydratesG = 0;
        int totalSugarsG = 0;
        int totalFatG = 0;
        int totalTransFatG = 0;
        int totalSaturatedFatG = 0;
        int totalCholesterolMg = 0;
        int totalProteinG = 0;

        // 식단 정보들의 영양소 합 저장
        for (DietRecord dietRecord : todayDietRecords) {
            totalCalorie += dietRecord.getCalorie();
            totalSodiumMg += dietRecord.getSodium();
            totalCarbohydratesG += (int)dietRecord.getCarbohydrate();
            totalSugarsG += (int)dietRecord.getSugar();
            totalFatG += (int)dietRecord.getFat();
            totalTransFatG += (int)dietRecord.getTransFat();
            totalSaturatedFatG += (int)dietRecord.getSaturatedFat();
            totalCholesterolMg += dietRecord.getCholesterol();
            totalProteinG += (int)dietRecord.getProtein();
        }

        // 오늘 먹었던 식단들의 영양소 합을 응답한다.
        return TodayNutritionResponse.builder()
                .calorie(totalCalorie)
                .sodiumMg(totalSodiumMg)
                .carbohydratesG(totalCarbohydratesG)
                .sugarsG(totalSugarsG)
                .fatG(totalFatG)
                .transFatG(totalTransFatG)
                .saturatedFatG(totalSaturatedFatG)
                .cholesterolMg(totalCholesterolMg)
                .proteinG(totalProteinG)
                .build();
    }

    public FeedbackResponse getFeedback(Long currentUserId) {
        Feedback feedback = feedbackRepository.findByUserId(currentUserId);
        FeedbackResponse feedbackResponse = new FeedbackResponse();
        feedbackResponse.setFeedback(feedback.getFeedback());
        return feedbackResponse;
    }
}
