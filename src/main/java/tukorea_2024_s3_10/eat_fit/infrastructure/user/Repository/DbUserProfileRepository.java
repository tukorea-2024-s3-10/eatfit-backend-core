package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserProfileRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DbUserProfileRepository implements UserProfileRepository {
    final JpaUserProfileRepository userProfileRepository;

    @Override
    public Optional<BodyProfile> findById(Long id) {
        return null;
    }

    @Override
    public BodyProfile save(BodyProfile bodyProfile) {
        return userProfileRepository.save(bodyProfile);
    }

}
