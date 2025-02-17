package tukorea_2024_s3_10.eat_fit.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.food.dto.DietRecordRequest;

@Service
@RequiredArgsConstructor
public class DietRecordService {
    private final DietRecordRepository dietRecordRepository;
    private final UserRepository userRepository;

    @Transactional
    public void recordDiet(DietRecordRequest dietRecordRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user =userRepository.findById(currentUserId).get();

        DietRecord dietRecord = DietRecord.builder()
                .user(user)
                .date(dietRecordRequest.getDate())
                .mealType(dietRecordRequest.getMealType())
                .foodName(dietRecordRequest.getFoodName())
                .mass(dietRecordRequest.getMass())
                .calorie(dietRecordRequest.getCalorie())
                .carbohydrate(dietRecordRequest.getCarbohydrate())
                .sugar(dietRecordRequest.getSugar())
                .protein(dietRecordRequest.getProtein())
                .fat(dietRecordRequest.getFat())
                .saturatedFat(dietRecordRequest.getSaturatedFat())
                .transFat(dietRecordRequest.getTransFat())
                .sodiumGoal(dietRecordRequest.getSodiumGoal())
                .cholesterol(dietRecordRequest.getCholesterol())
                .build();

        dietRecordRepository.save(dietRecord);
    }



}
