package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.domain.food.repository.FoodRepository;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

}
