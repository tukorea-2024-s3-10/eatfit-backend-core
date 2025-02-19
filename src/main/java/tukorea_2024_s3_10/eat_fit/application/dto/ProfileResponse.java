package tukorea_2024_s3_10.eat_fit.application.dto;
import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.UserProfile;

@Getter
@Setter
public class ProfileResponse {
    private String name;
    private String gender;
    private int birthYear;
    private int height;
    private double weight;
    private String goalCategory;
    private double targetWeight;
    private String disease;

    public ProfileResponse(UserProfile userProfile) {
        this.name = userProfile.getName();
        this.gender = userProfile.getGender();
        this.birthYear = userProfile.getBirthYear();
        this.height = userProfile.getHeight();
        this.weight = userProfile.getWeight();
        this.goalCategory = userProfile.getGoalCategory();
        this.targetWeight = userProfile.getTargetWeight();
        this.disease = userProfile.getDisease();
    }
}
