package tukorea_2024_s3_10.eat_fit.domain.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;

import java.util.List;
import java.util.Optional;

public interface FoodRepository{
    List<Food> findByName(String name);
}