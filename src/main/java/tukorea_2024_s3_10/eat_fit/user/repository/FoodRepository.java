package tukorea_2024_s3_10.eat_fit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.user.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
