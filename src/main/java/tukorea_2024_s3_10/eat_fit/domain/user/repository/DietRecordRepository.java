package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tukorea_2024_s3_10.eat_fit.batch.UserDietSummary;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DietRecordRepository extends JpaRepository<DietRecord, Long> {
    DietRecord save(DietRecord dietRecord);
    List<DietRecord> findByUserIdAndDate(long userId, LocalDate date);
    Optional<DietRecord> findById(long id);
    List<DietRecord> findByUserId(long userId);

    @Query(value = """
    SELECT dr.user_id AS userId, GROUP_CONCAT(dr.food_name) AS foods
    FROM diet_record dr
    WHERE DATE(dr.date) = :date
    GROUP BY dr.user_id
    """, nativeQuery = true)
    List<Object[]> findUserDietSummaryByDate(@Param("date") LocalDate date);

}