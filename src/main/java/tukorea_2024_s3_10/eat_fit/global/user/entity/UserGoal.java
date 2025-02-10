package tukorea_2024_s3_10.eat_fit.global.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGoal {

    private Long id; // 고유 id
    private String email; // 이메일
    private String gender; // 성별
    private int birthYear; // 출생연도
    private int height; // 키(신장)
    private double weight; // 몸무게
    private String goal; // 사용자 목표
    private String goalWeight; // 목표 몸무게
    private int goalKcal; // 목표 섭취 칼로리
    private double goalCarbs; // 목표 섭취 탄수화물
    private double goalProtein; // 목표 섭취 단백질
    private double goalFats; // 목표 섭취 지방
}
