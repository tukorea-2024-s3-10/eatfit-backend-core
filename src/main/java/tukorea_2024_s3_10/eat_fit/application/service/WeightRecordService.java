package tukorea_2024_s3_10.eat_fit.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.WeightRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.WeightRecordRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;
import tukorea_2024_s3_10.eat_fit.presentation.user.dto.WeightRecordRequest;

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

}
