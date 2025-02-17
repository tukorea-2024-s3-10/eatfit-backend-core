package tukorea_2024_s3_10.eat_fit.infrastructure.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.food.repository.FoodRepository;

import java.util.List;


public interface JpaFoodRepository extends JpaRepository<Food, Long>{
    List<Food> findByName(String name);
}
