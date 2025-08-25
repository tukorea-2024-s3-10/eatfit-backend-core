package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserDisease;

import java.util.Optional;

public interface UserDiseaseRepository extends JpaRepository<UserDisease, Long> {
    Optional<UserDisease> findByUserId(Long userId);
}
