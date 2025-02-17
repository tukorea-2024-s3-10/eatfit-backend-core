package tukorea_2024_s3_10.eat_fit.presentation.food.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.application.dto.DietRecordResponse;
import tukorea_2024_s3_10.eat_fit.application.service.DietRecordService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.presentation.food.dto.DietRecordRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/dietrecord")
public class DietRecordController {

    private final DietRecordService dietRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> recodeDiet(@RequestBody DietRecordRequest request) {
        dietRecordService.recordDiet(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
