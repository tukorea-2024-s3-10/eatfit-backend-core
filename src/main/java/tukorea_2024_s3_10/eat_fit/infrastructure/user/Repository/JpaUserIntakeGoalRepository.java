package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;

import java.util.Optional;

public interface JpaUserIntakeGoalRepository extends JpaRepository<UserIntakeGoal, Long> {
    Optional<UserIntakeGoal> findByUserId(Long userId);
}
