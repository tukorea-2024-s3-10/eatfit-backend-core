package tukorea_2024_s3_10.eat_fit.domain.user;

import jakarta.persistence.*;
import lombok.*;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.User;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeightRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private double weight;
    private LocalDate date;
}