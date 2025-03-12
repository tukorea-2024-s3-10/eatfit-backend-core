package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.UserIntakeGoal;
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
    public Optional<UserIntakeGoal> findById(Long id){
        return userIntakeGoalRepository.findById(id);
    }
}
