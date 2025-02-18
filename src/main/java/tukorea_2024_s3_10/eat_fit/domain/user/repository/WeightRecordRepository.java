package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.WeightRecord;

import java.util.Optional;

public interface WeightRecordRepository {
    WeightRecord save(WeightRecord weightRecord);
    Optional<WeightRecord> findById(Long id);
}