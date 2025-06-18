package tukorea_2024_s3_10.eat_fit.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job feedbackJob;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
    public void scheduleFeedbackJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // JobInstance 식별용
                .toJobParameters();
        jobLauncher.run(feedbackJob, params);
    }
}

