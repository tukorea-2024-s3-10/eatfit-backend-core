package tukorea_2024_s3_10.eat_fit.presentation.user.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSetupRequest {
    private MultipartFile profileImage; // 프로필 사진
    private String nickname; // 닉네임
    private String gender; // 성별
    private int age; // 나이
    private double height; // 키
    private double weight; // 몸무게
    private String goalType; // 목표 종류
    @Builder.Default
    private List<String> diseases = new ArrayList<>(); // 질병
    private double targetWeight; // 목표 몸무게
}
