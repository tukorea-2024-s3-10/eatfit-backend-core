package tukorea_2024_s3_10.eat_fit.presentation.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.FoodResponse;
import tukorea_2024_s3_10.eat_fit.application.service.FoodService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/food")
public class FoodController {
    private final FoodService foodService;

    @GetMapping
    @Operation(summary = "음식명으로 음식 정보 검색", description = "음식 이름으로 음식 정보 조회(List로 반환)")
    public ResponseEntity<ApiResponse<List<FoodResponse>>> getFoods(@RequestParam String name){
        List<FoodResponse> foods = foodService.getFoodByName(name);
        return ResponseEntity.ok(ApiResponse.success(foods));
    }

}
