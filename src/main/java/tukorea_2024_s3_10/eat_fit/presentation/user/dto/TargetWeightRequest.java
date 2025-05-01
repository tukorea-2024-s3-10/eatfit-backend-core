package tukorea_2024_s3_10.eat_fit.presentation.user.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetWeightRequest {

    @Min(value = 30, message = "목표 체중은 30kg 이상이어야 합니다.")
    @Max(value = 200, message = "목표 체중은 200kg 이하이어야 합니다.")
    private double targetWeight;
}