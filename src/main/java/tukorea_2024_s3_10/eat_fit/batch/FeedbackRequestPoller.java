package tukorea_2024_s3_10.eat_fit.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FeedbackRequestPoller {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.diet-feedback-request-url}")
    private String queueUrl;

    // 피드백 생성 서비스 (비즈니스 로직 담당)
    private final FeedbackService feedbackService;

    // 3초마다 polling
    @Scheduled(fixedDelay = 3000)
    public void pollMessages() {
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(10) // 한 번에 최대 10개
                .waitTimeSeconds(1)
                .build();

        List<Message> messages = sqsClient.receiveMessage(request).messages();

        for (Message message : messages) {
            try {
                UserDietSummary summary = objectMapper.readValue(
                        message.body(), UserDietSummary.class);

                feedbackService.generateFeedback(summary); // 실제 피드백 생성

                // 처리 완료 후 삭제
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build());

            } catch (Exception e) {
                System.err.println("메시지 처리 실패: " + e.getMessage());
            }
        }
    }
}

