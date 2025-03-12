package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;

import java.util.List;

@Getter
@Setter
public class TodayIntakeResponse {
    private int calorie; // Kcal
    private double carbohydrate; // g
    private double sugar; // g
    private double protein; // g
    private double fat; // g
    private double saturatedFat; // g
    private double transFat; // g
    private int sodiumGoal; // mg
    private int cholesterol; // mg

    public TodayIntakeResponse(List<DietRecord> todayDietRecords) {
        for (DietRecord dietRecord : todayDietRecords) {
            calorie += dietRecord.getCalorie();
            carbohydrate += dietRecord.getCarbohydrate();
            sugar += dietRecord.getSugar();
            protein += dietRecord.getProtein();
            fat += dietRecord.getFat();
            saturatedFat += dietRecord.getSaturatedFat();
            transFat += dietRecord.getTransFat();
            sodiumGoal += dietRecord.getSodiumGoal();
            cholesterol += dietRecord.getCholesterol();
        }
    }
}
