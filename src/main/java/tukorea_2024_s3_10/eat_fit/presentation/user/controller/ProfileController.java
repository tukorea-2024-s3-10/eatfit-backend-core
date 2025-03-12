package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.application.dto.ProfileResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileEditRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.ProfileInitRequest;
import tukorea_2024_s3_10.eat_fit.application.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/profile")
public class ProfileController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> initializeProfile(@RequestBody ProfileInitRequest profileInitRequest) {
        userService.initProfile(profileInitRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProfileResponse>> getUserProfile() {
        return ResponseEntity.ok(ApiResponse.success(userService.getProfile()));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> editProfile(@RequestBody ProfileEditRequest profileEditRequest) {
        userService.editProfile(profileEditRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}