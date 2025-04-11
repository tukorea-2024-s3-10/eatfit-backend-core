package tukorea_2024_s3_10.eat_fit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BodyProfile {

    @Id
    private Long userId;

    private String gender; // 성별

    private int age; // 나이

    @Column(columnDefinition = "DECIMAL(4,1)")
    private double height;

    @Column(columnDefinition = "DECIMAL(4,1)")
    private double weight;

    private String goalType; // 목표 타입

    @Column(columnDefinition = "DECIMAL(4,1)")
    private double targetWeight;
}
