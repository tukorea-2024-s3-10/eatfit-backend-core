package tukorea_2024_s3_10.eat_fit.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.WeightRecordResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;
import tukorea_2024_s3_10.eat_fit.domain.user.WeightRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.WeightRecordRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordRequest;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordEditRequest;

@Service
@RequiredArgsConstructor
public class WeightRecordService {
    private final WeightRecordRepository weightRecordRepository;
    private final UserRepository userRepository;

    public void recordWeight(WeightRecordRequest weightRecordRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user =userRepository.findById(currentUserId).get();

        WeightRecord weightRecord = WeightRecord.builder()
                .user(user)
                .weight(weightRecordRequest.getWeight())
                .date(weightRecordRequest.getDate())
                .build();

        weightRecordRepository.save(weightRecord);
    }

    @Transactional
    public void editWeightRecord(WeightRecordEditRequest weightRecordEditRequest) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        WeightRecord weightRecord = weightRecordRepository.findById(weightRecordEditRequest.getId()).get();

        User user =userRepository.findById(currentUserId).get();

        // 예외 처리 필요
        if(!weightRecord.getUser().getId().equals(user.getId())) {
            return;
        }

        weightRecord.setWeight(weightRecordEditRequest.getWeight());

        weightRecordRepository.save(weightRecord);

    }

    public WeightRecordResponse getWeightRecord(Long id){
        WeightRecord weightRecord = weightRecordRepository.findById(id).get();

        return new WeightRecordResponse(weightRecord);
    }

}
