package tukorea_2024_s3_10.eat_fit.application.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tukorea_2024_s3_10.eat_fit.application.dto.DinnerMenuResponse;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.FoodItem;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class DinnerMenuService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;
    private static final String OPENAI_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final DietRecordRepository dietRecordRepository;
    private final UserIntakeGoalRepository userIntakeGoalRepository;

    // 음식명과 이미지 URL 매핑
    private static final Map<String, String> foodImageMap = Map.ofEntries(
            Map.entry("간장게장", "https://i.namu.wiki/i/vUw12c7iwLUBQqgZ3cpp05yFeakqOeSSmAaoFWInvkftwEnT2plZFbrMbQsrIPtog7Mbad0huqTzdsHV_nBB1g.webp"),
            Map.entry("갈비찜", "https://i.namu.wiki/i/9xHkxjyIHj2yj9fFf7eeyK8YJE3Lu3gJpFEDpe8cNwuMl2hOm61RE7S6607J1KwdvvcejL2J2b2kdS6y_UL0JQ.webp"),
            Map.entry("비빔밥", "https://yourcdn.com/images/bibimbap.jpg")
            // ... 나머지 음식 등록
    );

    public DinnerMenuResponse getDinnerMenu() {
        return generateDinnerMenu();
    }

    public DinnerMenuResponse generateDinnerMenu() {
        try {
            // 프롬프트 구성 및 API 호출
            String prompt = buildPrompt();
            String aiResponse = callChatGPT(prompt);

            // 응답 예시: "탄수화물. 김치찌개, 불고기, 비빔밥"
            String[] parts = aiResponse.split("\\.");
            String nutrient = parts[0].trim();
            String[] foods = parts[1].trim().split(",\\s*");

            List<FoodItem> foodItems = new ArrayList<>();
            for (String food : foods) {
                String imageUrl = foodImageMap.getOrDefault(food, "https://yourcdn.com/images/default.jpg");
                foodItems.add(new FoodItem(food, imageUrl));
            }

            return new DinnerMenuResponse(nutrient, foodItems);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("OpenAI API 호출 실패", e);
        }
    }

    private String callChatGPT(String prompt) throws Exception {
        ObjectNode requestBody = objectMapper.createObjectNode();
        requestBody.put("model", "gpt-3.5-turbo");
        ArrayNode messages = requestBody.putArray("messages");

        ObjectNode userMessage = objectMapper.createObjectNode();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);
        messages.add(userMessage);

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

        JsonNode jsonNode = objectMapper.readTree(response.body());
        return jsonNode
                .path("choices").get(0)
                .path("message")
                .path("content")
                .asText();
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

        String foodList = String.join(", ",
                "간장게장", "갈비찜", "갈비탕", "갈치조림", "감자채볶음", "감자탕", "갓김치", "계란국",
                "계란말이", "계란찜", "계란후라이", "고등어구이", "고사리나물", "곱창구이", "김밥", "김치볶음밥",
                "김치전", "김치찌개", "김치찜", "까르보나라", "깍두기", "깻잎장아찌", "누룽지", "닭갈비",
                "닭볶음탕", "도토리묵", "동그랑땡", "된장찌개", "두부김치", "떡갈비", "떡국", "떡꼬치", "떡볶이",
                "라면", "마카롱", "만두", "매운탕", "무국", "물냉면", "미역국", "미역줄기볶음", "배추김치",
                "백김치", "보쌈", "불고기", "비빔냉면", "비빔밥", "삼겹살", "삼계탕", "새우볶음밥", "새우튀김",
                "설렁탕", "소세지볶음", "송편", "수제비", "숙주나물", "순대", "순두부찌개", "스테이크", "스파게티",
                "시금치나물", "쌀국수", "애호박볶음", "양념게장", "양념치킨", "어묵볶음", "열무김치", "오이소박이",
                "오징어튀김", "와플", "유부초밥", "육개장", "육회", "잔치국수", "잡채", "장조림", "제육볶음",
                "족발", "주꾸미볶음", "짜장면", "짬뽕", "쫄면", "찜닭", "초밥", "총각김치", "추어탕", "칼국수",
                "콩국수", "콩나물국", "콩나물무침", "콩자반", "타코야끼", "파김치", "파전", "피자", "해물찜",
                "햄버거", "후라이드치킨", "훈제오리"
        );


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

        return String.format(
                "오늘 가장 부족한 영양소는 %s입니다. " +
                        "%s을 보충하기 좋은 음식 3가지를 아래 제공된 음식 리스트에서만 고르세요. " +
                        "반드시 리스트에 없는 음식은 절대 고르지 마세요. " +
                        "출력은 반드시 다음 형식으로만 하세요: '%s. 음식1, 음식2, 음식3' " +
                        "예시 문구나 설명은 쓰지 마세요. " +
                        "음식 리스트: %s",
                deficientNutrient, deficientNutrient, deficientNutrient, foodList
        );
    }


}
