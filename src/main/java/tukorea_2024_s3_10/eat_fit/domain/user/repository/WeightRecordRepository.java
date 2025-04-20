package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.user.WeightRecord;

import java.util.List;
import java.util.Optional;

public interface WeightRecordRepository {
    WeightRecord save(WeightRecord weightRecord);
    List<WeightRecord> findByUserId(Long id);
    Optional<WeightRecord> findById(Long id);
}