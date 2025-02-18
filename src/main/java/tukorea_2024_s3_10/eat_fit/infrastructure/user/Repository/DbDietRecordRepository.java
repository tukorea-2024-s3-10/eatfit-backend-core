package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DbDietRecordRepository implements DietRecordRepository {
    private final JpaDietRecordRepository dietRecordRepository;

    @Override
    public DietRecord save(DietRecord dietRecord){
        return dietRecordRepository.save(dietRecord);
    }

    @Override
    public List<DietRecord> findByUserIdAndDate(long userId, LocalDate date){
        dietRecordRepository.findByUserIdAndDate(userId, date);
    }
}
