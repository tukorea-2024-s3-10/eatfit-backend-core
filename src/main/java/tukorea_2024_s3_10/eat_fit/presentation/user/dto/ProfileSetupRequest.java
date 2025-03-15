package tukorea_2024_s3_10.eat_fit.presentation.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProfileSetupRequest {
    private MultipartFile profileImage; // 프로필 사진
    private String nickname; // 닉네임
    private String gender; // 성별
    private int age; // 나이
    private double height; // 키
    private double weight; // 몸무게
    private String targetType; // 목표 종류
    private double targetWeight; // 목표 몸무게
    private List<String> diseases; // 질병
}
