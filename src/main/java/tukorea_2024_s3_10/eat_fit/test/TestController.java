package tukorea_2024_s3_10.eat_fit.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.user.TodayNutritionResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.batch.DietReader;
import tukorea_2024_s3_10.eat_fit.batch.UserDietSummary;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;
    private final DietReader dietReader;

//    @GetMapping("/api/test/users/{userId}/nutrition/today")
//    public ResponseEntity<ApiResponse<TodayNutritionResponse>> getTodayNutrition(@PathVariable Long userId) {
//        TodayNutritionResponse todayNutritionResponse = userService.getTodayNutrition(userId);
//        return ResponseEntity.ok(ApiResponse.success(todayNutritionResponse));
//    }

    @GetMapping("/test/diet-reader")
    public String testReader() throws Exception {
        dietReader.init();
        StringBuilder output = new StringBuilder();
        UserDietSummary summary;

        while ((summary = dietReader.read()) != null) {
            output.append("User ID: ").append(summary.userId()).append("\n");
            output.append("Foods: ").append(String.join(", ", summary.foods())).append("\n\n");
        }

        // 다시 실행하면 read()는 null만 반환하므로 참고용
        return output.toString().isEmpty() ? "No data to read." : output.toString();
    }
}
