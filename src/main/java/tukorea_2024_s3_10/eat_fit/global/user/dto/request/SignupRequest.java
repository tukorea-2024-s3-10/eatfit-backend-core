package tukorea_2024_s3_10.eat_fit.global.user.dto.request;

import lombok.Getter;

@Getter
public class SignupRequest {

    private String email;

    private String password;

    private String name;

    private String gender; // 성별
    private int birthYear; // 출생연도
    private int height; // 키(신장)
    private double weight; // 몸무게
    private String goal; // 사용자 목표
    private String goalWeight; // 목표 몸무게
}
