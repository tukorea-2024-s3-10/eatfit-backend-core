package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.TodayIntakeResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/current-intake")
    public ResponseEntity<ApiResponse<TodayIntakeResponse>> getCurrentIntake() {
        TodayIntakeResponse todayIntakeResponse = userService.calculateTodayIntake();
        return ResponseEntity.ok(ApiResponse.success(todayIntakeResponse));
    }
}
