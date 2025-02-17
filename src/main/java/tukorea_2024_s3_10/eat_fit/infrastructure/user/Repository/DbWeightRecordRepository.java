package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.WeightRecordRepository;


@Repository
@RequiredArgsConstructor
public class DbWeightRecordRepository implements WeightRecordRepository {
    private final JpaWeightRecordRepository weightRecordRepository;
}
