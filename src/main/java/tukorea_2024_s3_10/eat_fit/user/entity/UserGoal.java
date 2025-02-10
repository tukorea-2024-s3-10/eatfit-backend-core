package tukorea_2024_s3_10.eat_fit.user.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 id

    @OneToOne
    private User user;

    private String gender; // 성별
    private int birthYear; // 출생연도
    private int height; // 키(신장)
    private double weight; // 몸무게
    private String goalType; // 사용자 목표
    private String goalWeight; // 목표 몸무게
    private int goalKcal; // 목표 섭취 칼로리
    private double goalCarbs; // 목표 섭취 탄수화물
    private double goalProtein; // 목표 섭취 단백질
    private double goalFats; // 목표 섭취 지방
}
