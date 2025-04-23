package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;

import java.util.Optional;

public interface UserIntakeGoalRepository {
    Optional<UserIntakeGoal> findByUserId(Long userId);
    UserIntakeGoal save(UserIntakeGoal userIntakeGoal);
}
