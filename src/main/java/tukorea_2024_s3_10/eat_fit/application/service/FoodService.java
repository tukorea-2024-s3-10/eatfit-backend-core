package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.FoodResponse;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;
import tukorea_2024_s3_10.eat_fit.domain.food.repository.FoodRepository;
import tukorea_2024_s3_10.eat_fit.presentation.food.dto.FoodRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<FoodResponse> getFoodByName(String name) {
        List<Food> foods = foodRepository.findByName(name);

        return foods.stream().map(FoodResponse::new).collect(Collectors.toList());
    }

}
