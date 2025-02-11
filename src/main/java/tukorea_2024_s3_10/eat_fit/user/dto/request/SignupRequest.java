package tukorea_2024_s3_10.eat_fit.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
    private final String email;
    private final String password;
    private final String name;
    private final String gender; // 성별
    private final int birthYear; // 출생연도
    private final int height; // 키
    private final double weight; // 몸무게
    private final String goalCategory; // 목표 종류
    private final double targetWeight; // 목표 몸무게
    private final String disease;
}
