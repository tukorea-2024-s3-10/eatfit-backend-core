package tukorea_2024_s3_10.eat_fit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;

import java.util.Date;

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

    @ManyToOne
    private User user;

    private double weight;
    private Date date;
}