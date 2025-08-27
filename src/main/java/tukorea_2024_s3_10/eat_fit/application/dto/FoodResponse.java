package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.Food;

@Getter
@Setter
public class FoodResponse {
    private String name;
    private int mass; // 음식 질량 (g)
    private int calorie; // Kcal
    private double carbohydrate; // g
    private double sugar; // g
    private double protein; // g
    private double fat; // g
    private double saturatedFat; // g
    private double transFat; // g
    private int sodiumGoal; // mg
    private int cholesterol; // mg

    public FoodResponse(Food food) {
        this.name = food.getName();
        this.mass = food.getMass();
        this.calorie = food.getCalorie();
        this.carbohydrate = food.getCarbohydrate();
        this.sugar = food.getSugar();
        this.protein = food.getProtein();
        this.fat = food.getFat();
        this.saturatedFat = food.getSaturatedFat();
        this.transFat = food.getTransFat();
        this.sodiumGoal = food.getSodiumGoal();
        this.cholesterol = food.getCholesterol();
    }
}
