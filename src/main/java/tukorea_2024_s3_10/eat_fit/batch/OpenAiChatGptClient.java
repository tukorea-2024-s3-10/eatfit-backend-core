package tukorea_2024_s3_10.eat_fit.batch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class OpenAiChatGptClient {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public String generateFeedback(UserDietSummary summary) {
        try {
            // 프롬프트 구성
            String prompt = buildPrompt(summary);

            // 요청 바디 구성
            ObjectNode requestBody = objectMapper.createObjectNode();
            // 테스트용으로 gpt-3.5-turbo 추천 (권한 없으면 gpt-4 호출 실패)
            requestBody.put("model", "gpt-3.5-turbo");
            ArrayNode messages = requestBody.putArray("messages");

            ObjectNode systemMessage = objectMapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content", "다음은 사용자가 어제 먹은 음식들이야. 최대 3문장 정도로 간단하게 피드백을 작성해줘. '문장의 시작은 어제 식단을 분석해본 결과'로 시작하도록 ");
            messages.add(systemMessage);

            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);

            // HTTP 호출 준비 (HTTP 1.1 사용)
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_ENDPOINT))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // 응답 파싱 및 반환
            JsonNode jsonNode = objectMapper.readTree(response.body());
            return jsonNode
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("OpenAI API 호출 실패", e);
        }
    }

    private String buildPrompt(UserDietSummary summary) {
        return String.format(
                "먹은 음식들: %s\n\n이 식단을 분석하고 간단한 피드백과 개선점을 제시해주세요.",
                String.join(", ", summary.foods())
        );
    }
}
