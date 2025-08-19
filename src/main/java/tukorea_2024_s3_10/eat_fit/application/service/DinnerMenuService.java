package tukorea_2024_s3_10.eat_fit.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.DinnerMenuResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.UserIntakeGoalRepository;
import tukorea_2024_s3_10.eat_fit.security.util.SecurityUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;



@Service
@RequiredArgsConstructor
public class DinnerMenuService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final DietRecordRepository dietRecordRepository;
    private final UserIntakeGoalRepository userIntakeGoalRepository;

    public DinnerMenuResponse getDinnerMenu() {
        return new DinnerMenuResponse(generateDinnerMenu());
    }


    public String generateDinnerMenu() {
        try {
            // 프롬프트 구성
            String prompt = buildPrompt();

            // 요청 바디 구성
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", "gpt-3.5-turbo");
            ArrayNode messages = requestBody.putArray("messages");

            // systemMessage 제거 → 필요 없음
            ObjectNode userMessage = objectMapper.createObjectNode();
            userMessage.put("role", "user");
            userMessage.put("content", prompt);
            messages.add(userMessage);

            // HTTP 호출 준비
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


    private String buildPrompt() {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        LocalDate today = LocalDate.now();

        List<DietRecord> todayDietRecords = dietRecordRepository.findByUserIdAndDate(currentUserId, today);
        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findByUserId(currentUserId)
                .orElseThrow(() -> new RuntimeException("사용자의 영양 목표가 설정되지 않았습니다."));

        double carbRemain = userIntakeGoal.getCarbohydrateGoal();
        double proteinRemain = userIntakeGoal.getProteinGoal();
        double fatRemain = userIntakeGoal.getFatGoal();

        for (DietRecord dietRecord : todayDietRecords) {
            carbRemain -= dietRecord.getCarbohydrate();
            proteinRemain -= dietRecord.getProtein();
            fatRemain -= dietRecord.getFat();
        }

        // 음수 방지
        carbRemain = Math.max(0, carbRemain);
        proteinRemain = Math.max(0, proteinRemain);
        fatRemain = Math.max(0, fatRemain);

        // 가장 부족한 영양소 찾기
        String deficientNutrient;
        if (carbRemain >= proteinRemain && carbRemain >= fatRemain) {
            deficientNutrient = "탄수화물";
        } else if (proteinRemain >= carbRemain && proteinRemain >= fatRemain) {
            deficientNutrient = "단백질";
        } else {
            deficientNutrient = "지방";
        }

        // GPT에 전달할 프롬프트
        return String.format(
                "오늘 가장 부족한 영양소는 단백질입니다. " +
                        "단백질을 보충하기 좋은 음식 3가지를 골라주세요. " +
                        "출력은 반드시 다음 형식으로만 하세요: '단백질. 음식1, 음식2, 음식3' " +
                        "예시 문구나 설명은 쓰지 마세요."
        );
    }


}
