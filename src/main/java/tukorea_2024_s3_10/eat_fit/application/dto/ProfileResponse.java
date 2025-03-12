package tukorea_2024_s3_10.eat_fit.application.dto;
import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.BodyProfile;

@Getter
@Setter
public class ProfileResponse {
    private String name;
    private String gender;
    private int birthYear;
    private double height;
    private double weight;
    private String goalCategory;
    private double targetWeight;
    private String disease;

    public ProfileResponse(BodyProfile bodyProfile) {
        this.gender = bodyProfile.getGender();
        this.birthYear = bodyProfile.getAge();
        this.height = bodyProfile.getHeight();
        this.weight = bodyProfile.getWeight();
        this.goalCategory = bodyProfile.getTargetType();
        this.targetWeight = bodyProfile.getTargetWeight();
    }
}
