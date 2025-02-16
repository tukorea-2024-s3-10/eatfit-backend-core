package tukorea_2024_s3_10.eat_fit.presentation.food.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.service.FoodService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/food")
public class FoodController {
    private final FoodService foodService;

}
