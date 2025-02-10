package tukorea_2024_s3_10.eat_fit.global.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private String gender;

    private int birthYear;

    private int height;

    private double weight;

    private String goalType;

    private String goalKcal;

    private String goalTan;

    private String goalDan;

    private String goalJi;
}
