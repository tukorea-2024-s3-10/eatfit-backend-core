package tukorea_2024_s3_10.eat_fit.domain.user.repository;

import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByOauthId(String username);
    User save(User user);

    Optional<User> findById(Long currentUserId);
}
