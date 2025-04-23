package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserIntakeGoalRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DbUserIntakeGoalRepository implements UserIntakeGoalRepository {
    private final JpaUserIntakeGoalRepository userIntakeGoalRepository;

    @Override
    public UserIntakeGoal save(UserIntakeGoal userIntakeGoal){
        return userIntakeGoalRepository.save(userIntakeGoal);
    }

    @Override
    public Optional<UserIntakeGoal> findByUserId(Long userId){
        return userIntakeGoalRepository.findByUserId(userId);
    }
}
