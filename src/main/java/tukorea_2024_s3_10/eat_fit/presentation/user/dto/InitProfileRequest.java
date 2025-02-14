package tukorea_2024_s3_10.eat_fit.presentation.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InitProfileRequest {
    private String name;
    private String gender; // 성별
    private int birthYear; // 출생연도
    private int height; // 키
    private double weight; // 몸무게
    private String goalCategory; // 목표 종류
    private double targetWeight; // 목표 몸무게
    private String disease;
}
