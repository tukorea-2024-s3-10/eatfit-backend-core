package tukorea_2024_s3_10.eat_fit.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.user.TodayNutritionResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

    @GetMapping("/api/test/users/{userId}/nutrition/today")
    public ResponseEntity<ApiResponse<TodayNutritionResponse>> getTodayNutrition(@PathVariable Long userId) {
        TodayNutritionResponse todayNutritionResponse = userService.getTodayNutrition(userId);
        return ResponseEntity.ok(ApiResponse.success(todayNutritionResponse));
    }
}
