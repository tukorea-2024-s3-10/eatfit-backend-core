package tukorea_2024_s3_10.eat_fit.presentation.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tukorea_2024_s3_10.eat_fit.application.service.WeightRecordService;
import tukorea_2024_s3_10.eat_fit.global.dto.ApiResponse;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordRequest;

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
}
