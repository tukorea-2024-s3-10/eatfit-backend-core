package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.UserIntakeGoal;

import java.util.Optional;

public interface UserIntakeGoalRepository {
    Optional<UserIntakeGoal> findById(Long id);
    UserIntakeGoal save(UserIntakeGoal userIntakeGoal);
}
