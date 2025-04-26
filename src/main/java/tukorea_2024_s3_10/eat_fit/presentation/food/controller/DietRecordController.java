package tukorea_2024_s3_10.eat_fit.presentation.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.application.dto.DietRecordResponse;
import tukorea_2024_s3_10.eat_fit.application.service.DietRecordService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.presentation.food.dto.DietRecordRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/dietrecord")
public class DietRecordController {

    private final DietRecordService dietRecordService;

    @PostMapping
    @Operation(summary = "식단 기록 등록", description = "사용자의 식단 기록을 등록")
    public ResponseEntity<ApiResponse<Void>> recodeDiet(@RequestBody DietRecordRequest request) {
        dietRecordService.recordDiet(request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    @Operation(summary = "식단 기록 조회", description = "사용자의 식단 기록 조회(유저의 모든 식단 기록 반환)")
    public ResponseEntity<ApiResponse<List<DietRecordResponse>>> getDietRecord() {
        List<DietRecordResponse> dietRecordResponses = dietRecordService.getDietRecord();
        return ResponseEntity.ok(ApiResponse.success(dietRecordResponses));
    }

}
