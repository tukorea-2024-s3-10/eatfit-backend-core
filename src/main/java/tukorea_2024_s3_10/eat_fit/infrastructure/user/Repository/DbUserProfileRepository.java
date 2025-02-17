package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserProfileRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DbUserProfileRepository implements UserProfileRepository {
    final JpaUserProfileRepository userProfileRepository;

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userProfileRepository.findById(id);
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

}
