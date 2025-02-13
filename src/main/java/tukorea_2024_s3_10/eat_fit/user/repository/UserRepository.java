package tukorea_2024_s3_10.eat_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
