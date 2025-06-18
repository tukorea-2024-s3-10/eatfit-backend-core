package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.user.FeedbackResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.user.TodayNutritionResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class FeedbackController {

    private final UserService userService;

    @GetMapping("/me/feedback")
    public ResponseEntity<ApiResponse<FeedbackResponse>> getFeedback() {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        FeedbackResponse feedbackResponse = userService.getFeedback(currentUserId);
        return ResponseEntity.ok(ApiResponse.success(feedbackResponse));
    }

}

