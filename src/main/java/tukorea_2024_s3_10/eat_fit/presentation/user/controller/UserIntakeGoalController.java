package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.IntakeGoalResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/intake-goal")
public class UserIntakeGoalController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "사용자 목표 섭취량 설정", description = "사용자의 목표 섭취량을 계산하여 설정")
    public ResponseEntity<ApiResponse<IntakeGoalResponse>> getUserIntakeGoal(){
        return ResponseEntity.ok(ApiResponse.success(userService.getIntakeGoal()));
    }
}
