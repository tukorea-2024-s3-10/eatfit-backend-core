package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.DietRecord;

import java.time.LocalDate;

@Getter
@Setter
public class DietRecordResponse {
    private LocalDate date; // 날짜
    private String mealType; // 식사 유형 (아침, 점심, 간식 등)
    private String foodName;
    private int mass; // 음식 질량
    private int calorie; // Kcal
    private double carbohydrate; // g
    private double sugar; // g
    private double protein; // g
    private double fat; // g
    private double saturatedFat; // g
    private double transFat; // g
    private int sodiumGoal; // mg
    private int cholesterol; // mg
    private Long id;

    public DietRecordResponse(DietRecord dietRecord) {
        this.date = dietRecord.getDate();
        this.mealType = dietRecord.getMealType();
        this.foodName = dietRecord.getFoodName();
        this.mass = dietRecord.getMass();
        this.calorie = dietRecord.getCalorie();
        this.carbohydrate = dietRecord.getCarbohydrate();
        this.sugar = dietRecord.getSugar();
        this.protein = dietRecord.getProtein();
        this.fat = dietRecord.getFat();
        this.saturatedFat = dietRecord.getSaturatedFat();
        this.transFat = dietRecord.getTransFat();
        this.sodiumGoal = dietRecord.getSodium();
        this.cholesterol = dietRecord.getCholesterol();
        this.id = dietRecord.getId();
    }
}
