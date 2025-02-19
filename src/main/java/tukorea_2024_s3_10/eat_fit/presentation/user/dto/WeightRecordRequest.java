package tukorea_2024_s3_10.eat_fit.presentation.user.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WeightRecordRequest {
    private double weight;
    private LocalDate date;
}
