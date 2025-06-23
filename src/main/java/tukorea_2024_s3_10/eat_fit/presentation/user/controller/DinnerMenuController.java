package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.dto.DinnerMenuResponse;
import tukorea_2024_s3_10.eat_fit.application.service.DinnerMenuService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class DinnerMenuController {

    private final DinnerMenuService dinnerMenuService;

    @GetMapping("/dinner")
    public ResponseEntity<ApiResponse<DinnerMenuResponse>> getDinnerMenu() {
        DinnerMenuResponse response = dinnerMenuService.getDinnerMenu();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
