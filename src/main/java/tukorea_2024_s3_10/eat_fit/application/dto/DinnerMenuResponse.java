package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.food.entity.FoodItem;

import java.util.List;

@Getter
@Setter
public class DinnerMenuResponse {
    private String deficientNutrient;
    private List<FoodItem> foods;

    public DinnerMenuResponse(String deficientNutrient, List<FoodItem> foods) {
        this.deficientNutrient = deficientNutrient;
        this.foods = foods;
    }
}
