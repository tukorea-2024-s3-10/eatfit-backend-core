package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.application.service.ProfileService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileSetupRequest;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/profile")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;

    @PostMapping
    @Operation(summary = "가입 후 최초 프로필 설정", description = "서비스 가입 후 최초에 서비스 이용을 위한 초기 프로필 설정(신체 정보 및 목표 설정)")
    public ResponseEntity<ApiResponse<Void>> setupProfile(ProfileSetupRequest profileSetupRequest) {
        profileService.setupProfile(profileSetupRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    @Operation(summary = "유저 프로필 조회", description = "유저의 프로필 정보를 조회")
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile() {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile()));
    }

    @PatchMapping
    @Operation(summary = "유저 프로필 수정", description = "유저의 프로필 정보를 수정")
    public ResponseEntity<ApiResponse<Void>> editProfile(@RequestBody ProfileEditRequest profileEditRequest) {
        userService.editProfile(profileEditRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}