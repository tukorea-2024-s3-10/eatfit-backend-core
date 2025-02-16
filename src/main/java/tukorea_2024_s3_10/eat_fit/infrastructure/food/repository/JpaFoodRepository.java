package tukorea_2024_s3_10.eat_fit.infrastructure.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.food.repository.FoodRepository;

import java.util.List;

@Repository
public interface JpaFoodRepository extends FoodRepository, JpaRepository<Food, Long> {
}
