package tukorea_2024_s3_10.eat_fit.test;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class FeedbackJobTestController {

    private final JobLauncher jobLauncher;
    private final Job feedbackJob;

    @PostMapping("/batch/feedback")
    public ResponseEntity<String> runFeedbackJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 중복 방지를 위한 JobInstance 구분자
                    .toJobParameters();
            jobLauncher.run(feedbackJob, params);
            return ResponseEntity.ok("피드백 잡이 실행되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("잡 실행 중 오류 발생: " + e.getMessage());
        }
    }
}

