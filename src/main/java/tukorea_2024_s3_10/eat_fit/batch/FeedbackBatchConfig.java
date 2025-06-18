package tukorea_2024_s3_10.eat_fit.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class FeedbackBatchConfig {

    private final DietReader dietReader;
    private final FeedbackSqsWriter feedbackSqsWriter;

    @Bean
    public Step feedbackStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("feedbackStep", jobRepository)
                .<UserDietSummary, UserDietSummary>chunk(10, transactionManager)
                .reader(dietReader)
                .writer(feedbackSqsWriter)
                .build();
    }

    @Bean
    public Job feedbackJob(JobRepository jobRepository, Step feedbackStep) {
        return new JobBuilder("feedbackJob", jobRepository)
                .start(feedbackStep)
                .build();
    }
}
