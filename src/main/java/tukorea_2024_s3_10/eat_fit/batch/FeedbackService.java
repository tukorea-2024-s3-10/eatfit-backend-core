package tukorea_2024_s3_10.eat_fit.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final OpenAiChatGptClient chatGptClient;
    private final FeedbackRepository feedbackRepository;

    public void generateFeedback(UserDietSummary summary) {
        String feedback = chatGptClient.generateFeedback(summary);

        Feedback feedbackEntity = new Feedback();
        feedbackEntity.setUserId(summary.userId());
        feedbackEntity.setFeedback(feedback);
        feedbackRepository.save(feedbackEntity);
    }
}
