package tukorea_2024_s3_10.eat_fit.domain.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
}