package tukorea_2024_s3_10.eat_fit.domain.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserIntakeGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;             // 사용자별 권장 섭취량 계산

    private int calorieGoal;       // 칼로리
    private int sodiumGoal;        // 나트륨
    private double carbohydrateGoal;  // 탄수화물
    private double sugarGoal;         // 당류
    private double fatGoal;           // 지방
    private double transFatGoal;      // 트랜스지방
    private double saturatedFatGoal;  // 포화지방
    private int cholesterolGoal;   // 콜레스테롤
    private double proteinGoal;       // 단백질
}
