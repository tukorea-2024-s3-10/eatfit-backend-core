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
            // 테스트용으로 gpt-3.5-turbo 추천 (권한 없으면 gpt-4 호출 실패)
            requestBody.put("model", "gpt-3.5-turbo");
            ArrayNode messages = requestBody.putArray("messages");

            ObjectNode systemMessage = objectMapper.createObjectNode();
            systemMessage.put("role", "system");
            systemMessage.put("content",
                    "다음은 사용자가 오늘 섭취하지 못한 부족한 영양소 정보야. " +
                            "이 정보를 참고해서 저녁 메뉴 1개를 추천해줘. 추천은 총 3문장으로 해줘. " +
                            "첫 문장은 음식 이름만 간단히 제시하고, 그 뒤 두 문장은 그 음식이 어떤 부족한 영양소를 어떻게 보충해주는지를 설명해줘. " +
                            "모든 문장은 줄바꿈 없이 한 문장씩 공백으로 이어지게 만들어줘. 예시: " +
                            "'치킨 스테이크와 채소 샐러드. 이 메뉴는 단백질과 지방을 보충해줍니다. 특히 포화지방과 칼로리를 균형 있게 섭취할 수 있습니다.'"
            );
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
                "오늘 가장 부족한 영양소는 %s입니다. " +
                        "%s이 부족하네요. %s을 보충할 수 있는 한국 음식 3가지를 추천해 주세요. " +
                        "출력 형식은 반드시 ' %s이 부족하네요. 음식1, 음식2, 음식3 ' 처럼 해주세요. " +
                        "추가 설명이나 줄바꿈은 하지 마세요.",
                deficientNutrient, deficientNutrient, deficientNutrient, deficientNutrient
        );
    }

}
