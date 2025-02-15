package tukorea_2024_s3_10.eat_fit.infrastructure.user.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MysqlDbUserRepository implements UserRepository {

    private final SpringDataMysqlUserRepository userRepository;

    @Override
    public Optional<User> findByOauthId(String oAuthId) {
        return userRepository.findByOauthId(oAuthId);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
