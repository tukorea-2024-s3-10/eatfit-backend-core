package tukorea_2024_s3_10.eat_fit.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.DinnerMenuResponse;
import tukorea_2024_s3_10.eat_fit.batch.UserDietSummary;
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
            systemMessage.put("content", "다음은 사용자가 오늘 섭취하지 못한 부족한 영양소야. 이를 바탕으로 최대 3문장 정도로 추천하는 메뉴와 그 이유를 부족한 영양소를 설명하며 알려줘. 문장 시작할 때 음식 이름부터 말해줘.");
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
        // 오늘 먹었던 식단을 조회
        List<DietRecord> todayDietRecords = dietRecordRepository.findByUserIdAndDate(currentUserId, today);
        UserIntakeGoal userIntakeGoal = userIntakeGoalRepository.findByUserId(currentUserId).get();

        int calorie = userIntakeGoal.getCalorieGoal();       // 칼로리
        int sodiumMg = userIntakeGoal.getSodiumGoal();        // 나트륨
        double carbohydratesG = userIntakeGoal.getCarbohydrateGoal();  // 탄수화물
        double sugarsG = userIntakeGoal.getSugarGoal();         // 당류
        double fatG = userIntakeGoal.getFatGoal();           // 지방
        double transFatG = userIntakeGoal.getTransFatGoal();      // 트랜스지방
        double saturatedFatG = userIntakeGoal.getSaturatedFatGoal();  // 포화지방
        int cholesterolMg = userIntakeGoal.getCholesterolGoal();   // 콜레스테롤
        double proteinG = userIntakeGoal.getProteinGoal();

        for (DietRecord dietRecord : todayDietRecords) {
            calorie -= dietRecord.getCalorie();
            sodiumMg -= dietRecord.getSodiumGoal();
            carbohydratesG -= dietRecord.getCarbohydrate();
            sugarsG -= dietRecord.getSugar();
            fatG -= dietRecord.getFat();
            transFatG -= dietRecord.getTransFat();
            saturatedFatG -= dietRecord.getSaturatedFat();
            cholesterolMg -= dietRecord.getCholesterol();
            proteinG -= dietRecord.getProtein();
        }

        return String.format(
                "총 섭취 후 남은 영양 목표:\n"
                        + "- 칼로리: %d kcal\n"
                        + "- 나트륨: %d mg\n"
                        + "- 탄수화물: %.2f g\n"
                        + "- 당류: %.2f g\n"
                        + "- 지방: %.2f g\n"
                        + "- 트랜스지방: %.2f g\n"
                        + "- 포화지방: %.2f g\n"
                        + "- 콜레스테롤: %d mg\n"
                        + "- 단백질: %.2f g\n\n"
                        + "위 데이터를 참고하여 부족한 영양소를 보완할 수 있는 저녁 메뉴를 3문장 이내로 추천해 주세요.",
                calorie,
                sodiumMg,
                carbohydratesG,
                sugarsG,
                fatG,
                transFatG,
                saturatedFatG,
                cholesterolMg,
                proteinG
        );


    }
}
