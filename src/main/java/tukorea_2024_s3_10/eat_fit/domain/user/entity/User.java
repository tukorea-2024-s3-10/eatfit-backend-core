package tukorea_2024_s3_10.eat_fit.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유한 사용자 ID, 서비스 내에서 사용자 식별자로 사용

    private String oauthId; // 고유한 사용자 ID, 중복 가입 확인 용도

    private String nickname; // 서비스 내에서 사용되는 닉네임

    private String profileImageUrl; // S3에 저장된 프로필 사진 URL

    @Enumerated(EnumType.STRING)
    private Role role; // ROLE_GUEST or ROLE_USER. ROLE_GUEST = 가입 후 최초 프로필 설정을 마치지 않은 사용자

    @CreationTimestamp
    private Timestamp createdAt; // 가입 시간

    public void updateRole(Role role){
        this.role = role;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateProfileImageUrl(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
    }
}
