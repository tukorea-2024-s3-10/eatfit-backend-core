package tukorea_2024_s3_10.eat_fit.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import tukorea_2024_s3_10.eat_fit.domain.RefreshRepository;
import tukorea_2024_s3_10.eat_fit.infrastructure.jwt.JwtUtil;
import tukorea_2024_s3_10.eat_fit.security.handler.CustomOAuth2SuccessHandler;
import tukorea_2024_s3_10.eat_fit.infrastructure.jwt.JwtFilter;
import tukorea_2024_s3_10.eat_fit.security.service.CustomOAuth2UserService;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    //test
    @Value("${client_url}")
    private String clientUrl;

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customSuccessHandler;
    private final JwtUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JwtFilter(jwtUtil, refreshRepository), OAuth2LoginAuthenticationFilter.class)
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                )
                .authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll())
//                        .requestMatchers("/reissue","/swagger-ui/**").permitAll()
//                        .requestMatchers("/oauth2/**").permitAll() // 로그인 하지 않는 사용자들은 로그인 API만 호출 가능
//                        .requestMatchers("/api/core/users/profile").hasRole("GUEST") // GUEST 사용자는 /api/users만 호출 가능
//                        .requestMatchers("/api/**").hasRole("USER") // USER 사용자는 온전한 서비스 이용 가능
//                        .anyRequest().authenticated())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(clientUrl, "http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(List.of("Authorization")); // 액세스 토큰을 담은 헤더를 브라우저가 읽을 수 있도록 허용
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/health", "/swagger-ui/**", "/v3/api-docs/**"); // swagger api
    }
}
