package tukorea_2024_s3_10.eat_fit.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
