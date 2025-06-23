package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserIntakeGoal;

@Getter
@Setter
public class IntakeGoalResponse {
    private int calorieGoal;       // 칼로리
    private int sodiumGoal;        // 나트륨
    private int carbohydrateGoal;  // 탄수화물
    private double sugarGoal;         // 당류
    private int fatGoal;           // 지방
    private double transFatGoal;      // 트랜스지방
    private double saturatedFatGoal;  // 포화지방
    private int cholesterolGoal;   // 콜레스테롤
    private int proteinGoal;       // 단백질

    public IntakeGoalResponse(UserIntakeGoal userIntakeGoal) {
        calorieGoal = userIntakeGoal.getCalorieGoal();
        sodiumGoal = userIntakeGoal.getSodiumGoal();
        carbohydrateGoal = (int)userIntakeGoal.getCarbohydrateGoal();
        sugarGoal = userIntakeGoal.getSugarGoal();
        fatGoal = (int)userIntakeGoal.getFatGoal();
        transFatGoal = userIntakeGoal.getTransFatGoal();
        saturatedFatGoal = userIntakeGoal.getSaturatedFatGoal();
        cholesterolGoal = userIntakeGoal.getCholesterolGoal();
        proteinGoal = (int)userIntakeGoal.getProteinGoal();
    }
}
