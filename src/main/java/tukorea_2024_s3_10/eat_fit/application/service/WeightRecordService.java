package tukorea_2024_s3_10.eat_fit.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.WeightRecordResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.WeightRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.WeightRecordRepository;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordEditRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeightRecordService {
    private final WeightRecordRepository weightRecordRepository;
    private final UserRepository userRepository;

    public void recordWeight(WeightRecordRequest weightRecordRequest) {
        Long userId = SecurityUtil.getCurrentUserId();

        WeightRecord weightRecord = WeightRecord.builder()
                .userId(userId)
                .weight(weightRecordRequest.getWeight())
                .date(weightRecordRequest.getDate())
                .build();

        weightRecordRepository.save(weightRecord);
    }

    @Transactional
    public void editWeightRecord(WeightRecordEditRequest weightRecordEditRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        WeightRecord weightRecord = weightRecordRepository.findById(weightRecordEditRequest.getId()).get();

        // 예외 처리 필요
        if(!weightRecord.getUserId().equals(currentUserId)) {
            return;
        }

        weightRecord.setWeight(weightRecordEditRequest.getWeight());

        weightRecordRepository.save(weightRecord);

    }

    public List<WeightRecordResponse> getWeightRecord(){
        Long currentUserId = SecurityUtil.getCurrentUserId();
        List<WeightRecord> weightRecords = weightRecordRepository.findByUserId(currentUserId);

        return weightRecords.stream().map(WeightRecordResponse::new).collect(Collectors.toList());
    }

}
