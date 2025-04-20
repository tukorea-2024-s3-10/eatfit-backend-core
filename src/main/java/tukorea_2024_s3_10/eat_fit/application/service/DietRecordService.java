package tukorea_2024_s3_10.eat_fit.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.DietRecordResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.WeightRecordResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.food.dto.DietRecordRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietRecordService {
    private final DietRecordRepository dietRecordRepository;
    private final UserRepository userRepository;

    @Transactional
    public void recordDiet(DietRecordRequest dietRecordRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        DietRecord dietRecord = DietRecord.builder()
                .userId(currentUserId)
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

    public List<DietRecordResponse> getDietRecord(){
        Long currentUserId = SecurityUtil.getCurrentUserId();
        List<DietRecord> dietRecords = dietRecordRepository.findByUserId(currentUserId);
        return dietRecords.stream().map(DietRecordResponse::new).collect(Collectors.toList());
    }



}
