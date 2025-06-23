package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DinnerMenuResponse {
    private String dinnerMenu;
    public DinnerMenuResponse(String dinnerMenu) {
        this.dinnerMenu = dinnerMenu;
    }
}
