package tukorea_2024_s3_10.eat_fit.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.user.dto.request.SignupRequest;
import tukorea_2024_s3_10.eat_fit.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequest loginRequest){
//        userService.login(loginRequest);
//        return ResponseEntity.ok(ApiResponse.success(null));
//    }
}
