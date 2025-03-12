package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.WeightRecord;

import java.util.Optional;

public interface WeightRecordRepository {
    WeightRecord save(WeightRecord weightRecord);
    Optional<WeightRecord> findById(Long id);
}