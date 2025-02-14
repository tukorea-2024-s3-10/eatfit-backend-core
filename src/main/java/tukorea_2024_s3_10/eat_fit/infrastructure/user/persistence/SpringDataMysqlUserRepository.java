package tukorea_2024_s3_10.eat_fit.infrastructure.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;

import java.util.Optional;

public interface SpringDataMysqlUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOauthId(String oAuthId);
    Optional<User> findById(Long id);
}
