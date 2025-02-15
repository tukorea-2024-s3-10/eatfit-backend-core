package tukorea_2024_s3_10.eat_fit.domain.food;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int mass; // 음식 질량(g)
    private int calorie; // Kcal
    private double carbohydrate; // g
    private double sugar; // g
    private double protein; // g
    private double fat; // g
    private double saturatedFat; // g
    private double transFat; // g
    private int sodiumGoal; // mg
    private int cholesterol; // mg

}