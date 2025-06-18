package tukorea_2024_s3_10.eat_fit.batch;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Feedback {

    @Id
    Long userId;

    String feedback;
}
