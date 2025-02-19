package tukorea_2024_s3_10.eat_fit.application.dto;

import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.WeightRecord;

import java.time.LocalDate;

@Getter
@Setter
public class WeightRecordResponse {
    private double weight;
    private LocalDate date;

   public WeightRecordResponse(WeightRecord weightRecord) {
        weight = weightRecord.getWeight();
        date = weightRecord.getDate();
    }

}
