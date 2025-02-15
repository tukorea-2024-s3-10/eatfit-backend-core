package tukorea_2024_s3_10.eat_fit.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    private String name;
    private String gender;          // 성별
    private int birthYear;          // 출생연도
    private int height;             // 키

    @Column(scale = 1)
    private double weight;          // 몸무게

    private String goalCategory;    // 사용자 목표
    private double targetWeight;       // 목표 몸무게
    private String disease;         // 질병 (필수 X)
}
