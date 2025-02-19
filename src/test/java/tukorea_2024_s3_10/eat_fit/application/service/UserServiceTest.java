package tukorea_2024_s3_10.eat_fit.application.service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import tukorea_2024_s3_10.eat_fit.application.dto.TodayIntakeResponse;
import tukorea_2024_s3_10.eat_fit.domain.user.entity.DietRecord;
import tukorea_2024_s3_10.eat_fit.domain.user.repository.DietRecordRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private DietRecordRepository dietRecordRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void calculateTodayIntake() {
        long mockUserId = 1L;
        LocalDate today = LocalDate.now();

        // SecurityUtil에서 현재 사용자 ID를 가져오는 동작을 Mocking
        try (MockedStatic<SecurityUtil> mockedSecurityUtil = mockStatic(SecurityUtil.class)) {
            mockedSecurityUtil.when(SecurityUtil::getCurrentUserId).thenReturn(mockUserId);

            // 더미 데이터 생성
            DietRecord record1 = DietRecord.builder()
                    .id(1L)
                    .user(null) // user는 테스트에서 필요 없음
                    .date(today)
                    .mealType("아침")
                    .foodName("바나나")
                    .mass(120)
                    .calorie(105)
                    .carbohydrate(27)
                    .sugar(14)
                    .protein(1.3)
                    .fat(0.3)
                    .saturatedFat(0.1)
                    .transFat(0.0)
                    .sodiumGoal(1)
                    .cholesterol(0)
                    .build();

            DietRecord record2 = DietRecord.builder()
                    .id(2L)
                    .user(null)
                    .date(today)
                    .mealType("점심")
                    .foodName("닭가슴살")
                    .mass(150)
                    .calorie(165)
                    .carbohydrate(0)
                    .sugar(0)
                    .protein(31)
                    .fat(3.6)
                    .saturatedFat(1.0)
                    .transFat(0.0)
                    .sodiumGoal(70)
                    .cholesterol(85)
                    .build();

            List<DietRecord> dummyRecords = List.of(record1, record2);

            // dietRecordRepository.findByUserIdAndDate() 호출 시 더미 데이터를 반환하도록 설정
            when(dietRecordRepository.findByUserIdAndDate(mockUserId, today)).thenReturn(dummyRecords);

            // when: 메서드 실행
            TodayIntakeResponse response = userService.calculateTodayIntake();

            // then: 결과 검증
            assertNotNull(response);
            assertEquals(270, response.getCalorie()); // 105 + 165 = 270 Kcal
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
