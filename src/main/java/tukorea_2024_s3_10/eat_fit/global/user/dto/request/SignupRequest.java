package tukorea_2024_s3_10.eat_fit.global.user.dto.request;

import lombok.Getter;

@Getter
public class SignupRequest {

    private String email;

    private String password;

    private String name;

    private int height;

    private double weight;

    private String goalType;

    private String gender;

    private int birth;
}
