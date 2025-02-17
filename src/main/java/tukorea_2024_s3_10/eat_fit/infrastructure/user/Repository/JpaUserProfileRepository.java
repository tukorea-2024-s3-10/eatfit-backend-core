package tukorea_2024_s3_10.eat_fit.infrastructure.user.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;

import java.util.Optional;

public interface JpaUserProfileRepository extends JpaRepository<UserProfile, Long> {
}
