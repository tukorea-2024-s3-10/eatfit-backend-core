package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.user.WeightRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.WeightRecordRepository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class DbWeightRecordRepository implements WeightRecordRepository {
    private final JpaWeightRecordRepository weightRecordRepository;

    public WeightRecord save(WeightRecord weightRecord) {
        return weightRecordRepository.save(weightRecord);
    }

    public List<WeightRecord> findByUserId(Long userId) {
        return weightRecordRepository.findByUserId(userId);
    }

    public Optional<WeightRecord> findById(Long id) {
        return weightRecordRepository.findById(id);
    }
}
