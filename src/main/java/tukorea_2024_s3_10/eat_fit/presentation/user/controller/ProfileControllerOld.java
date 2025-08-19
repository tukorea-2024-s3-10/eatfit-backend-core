package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.application.dto.user.TargetWeightResponse;
import tukorea_2024_s3_10.eat_fit.application.service.ProfileService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileSetupRequest;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.TargetWeightRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/profile")
public class ProfileControllerOld {

    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    @Operation(summary = "마이페이지 사용자 정보 조회", description = "마이페이지 화면에 표시되는 사용자의 프로필 및 관련 정보를 조회합니다.")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile() {
        ProfileResponse profileResponse = userService.getProfile();
        return ResponseEntity.ok(ApiResponse.success(profileResponse));
    }

    @PostMapping
    @Operation(summary = "가입 후 최초 프로필 설정", description = "서비스 가입 후 최초에 서비스 이용을 위한 초기 프로필 설정(신체 정보 및 목표 설정)")
    public ResponseEntity<ApiResponse<Void>> setupProfile(@RequestBody ProfileSetupRequest profileSetupRequest) {
        profileService.setupProfile(profileSetupRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/target-weight")
    @Operation(summary = "목표 체중 설정 또는 수정", description = "사용자의 목표 체중을 설정하거나 수정하고, 해당 값에 따라 권장 섭취량을 자동 계산하여 저장")
    public ResponseEntity<ApiResponse<Void>> updateTargetWeight(@Valid @RequestBody TargetWeightRequest targetWeightRequest) {
        profileService.setTargetWeight(targetWeightRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping
    @Operation(summary = "유저 프로필 수정", description = "유저의 프로필 정보를 수정")
    public ResponseEntity<ApiResponse<Void>> editProfile(@RequestBody ProfileEditRequest profileEditRequest) {
        userService.editProfile(profileEditRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping("/target-weight")
    public ResponseEntity<ApiResponse<TargetWeightResponse>> getTargetWeight() {
        TargetWeightResponse targetWeightResponse = profileService.getTargetWeight();
        return ResponseEntity.ok(ApiResponse.success(targetWeightResponse));
    }
}