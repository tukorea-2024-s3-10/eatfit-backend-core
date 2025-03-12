package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;

public interface JpaUserProfileRepository extends JpaRepository<BodyProfile, Long> {
}
