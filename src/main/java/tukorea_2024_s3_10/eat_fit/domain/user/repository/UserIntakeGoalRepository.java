package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;

import java.util.Optional;

public interface UserIntakeGoalRepository {
    Optional<UserIntakeGoal> findById(Long id);
    UserIntakeGoal save(UserIntakeGoal userIntakeGoal);
}
