package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;

import java.util.Optional;

public interface BodyProfileRepository extends JpaRepository<BodyProfile, Long> {
    Optional<BodyProfile> findByUserId(Long userId);
}
