package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;

import java.util.Optional;

public interface UserProfileRepository{
    Optional<BodyProfile> findById(Long id);
    BodyProfile save(BodyProfile bodyProfile);
}
