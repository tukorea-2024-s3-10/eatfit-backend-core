package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "마이페이지 사용자 정보 조회", description = "마이페이지 화면에 표시되는 사용자의 프로필 및 관련 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        ProfileResponse profileResponse = userService.getProfile();
        return ResponseEntity.ok(ApiResponse.success(profileResponse));
    }
}
