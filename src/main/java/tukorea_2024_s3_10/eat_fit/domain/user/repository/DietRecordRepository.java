package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.entity.DietRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DietRecordRepository{
    DietRecord save(DietRecord dietRecord);
    List<DietRecord> findByUserIdAndDate(long userId, LocalDate date);
    Optional<DietRecord> findById(long id);
}