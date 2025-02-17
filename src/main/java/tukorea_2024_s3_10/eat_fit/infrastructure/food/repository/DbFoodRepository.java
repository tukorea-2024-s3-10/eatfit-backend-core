package tukorea_2024_s3_10.eat_fit.infrastructure.food.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.food.repository.FoodRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DbFoodRepository implements FoodRepository {
    private final JpaFoodRepository foodRepository;

    public List<Food> findByName(String name){
        return foodRepository.findByName(name);
    }

}
