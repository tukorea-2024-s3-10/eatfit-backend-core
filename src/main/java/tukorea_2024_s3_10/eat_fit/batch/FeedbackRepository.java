package tukorea_2024_s3_10.eat_fit.batch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByUserId(Long userId);
}
