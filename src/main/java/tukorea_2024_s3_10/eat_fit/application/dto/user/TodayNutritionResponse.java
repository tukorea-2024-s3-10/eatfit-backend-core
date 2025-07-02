package tukorea_2024_s3_10.eat_fit.application.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;

import java.util.List;

@Getter
@Setter
@Builder
public class TodayNutritionResponse {

    private int calorie;            // 칼로리
    private int sodiumMg;           // 나트륨 (mg)
    private int carbohydratesG;     // 탄수화물 (g)
    private int sugarsG;            // 당류 (g)
    private int fatG;               // 지방 (g)
    private int transFatG;          // 트랜스지방 (g)
    private int saturatedFatG;      // 포화지방 (g)
    private int cholesterolMg;      // 콜레스테롤 (mg)
    private int proteinG;           // 단백질 (g)
}
