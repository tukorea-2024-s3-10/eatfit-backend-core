package tukorea_2024_s3_10.eat_fit.application.service;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import tukorea_2024_s3_10.eat_fit.infrastructure.security.SecurityUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class ProfileServiceTest {

    @Test
    public void setupProfileTest() {
        MockedStatic<SecurityUtil> mockedSecurityUtil = mockStatic(SecurityUtil.class);
        mockedSecurityUtil.when(SecurityUtil::getCurrentUserId).thenReturn(1L);

        ProfileService profileService = new ProfileService();


    }
}