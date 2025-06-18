package tukorea_2024_s3_10.eat_fit.batch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;

import java.util.List;
@Component
@RequiredArgsConstructor
public class FeedbackSqsWriter implements ItemWriter<UserDietSummary> {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.diet-feedback-request-url}")
    private String queueUrl;

    @Override
    public void write(Chunk<? extends UserDietSummary> chunk) throws Exception {
        for (UserDietSummary summary : chunk) {
            String jsonBody = objectMapper.writeValueAsString(summary);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(jsonBody)
                    .build();

            sqsClient.sendMessage(request);
        }
    }
}