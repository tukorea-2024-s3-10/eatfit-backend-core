package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tukorea_2024_s3_10.eat_fit.application.dto.WeightRecordResponse;
import tukorea_2024_s3_10.eat_fit.application.service.WeightRecordService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordEditRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/core/users/weight")
public class WeightRecordController {

    private final WeightRecordService weightRecordService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> recordWeight(@RequestBody WeightRecordRequest weightRecordRequest){
        weightRecordService.recordWeight(weightRecordRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<Void>> editWeightRecord(@RequestBody WeightRecordEditRequest weightRecordEditRequest){
        weightRecordService.editWeightRecord(weightRecordEditRequest);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<WeightRecordResponse>> getAllWeightRecords(@RequestParam Long recordId){
        WeightRecordResponse weightRecordResponse = weightRecordService.getWeightRecord(recordId);
        return ResponseEntity.ok(ApiResponse.success(weightRecordResponse));
    }
}
