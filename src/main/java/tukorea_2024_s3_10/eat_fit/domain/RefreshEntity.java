package tukorea_2024_s3_10.eat_fit.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tukorea_2024_s3_10.eat_fit.domain.auth.User;

@Entity
@Getter
@Setter
public class RefreshEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(length = 500)
    private String refresh;

    private String expiration;
}