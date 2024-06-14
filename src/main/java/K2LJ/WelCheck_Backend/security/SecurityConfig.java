package K2LJ.WelCheck_Backend.security;

import K2LJ.WelCheck_Backend.security.LoginFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //** url경로 별 접근 권한 설정 **//
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/join", "/login", "/login/findUserId", "/login/findPassword", "/searchAddress").permitAll()	//루트, 회원가입, 로그인 페이지
                .requestMatchers(("/mypage")).authenticated()
                //.requestMatchers("myPage").hasAnyRole("ADMIN", "USER")	//마이페이지
                //.requestMatchers("").hasRole("")
                .anyRequest().denyAll()
        );
/*
        - requestMatchers(경로들) : 경로들에 대한 권한 설정
        - anyRequest() : requestMatchers에 사용된 경로들 제외한 나머지 경로들

        - permitAll() : requestMatchers의 경로들에 모든 사용자에게 접근 허용
        - authenticated() : requestMatchers의 경로들에 로그인만 하면 접근 허용
        - hasRole() : requestMatchers의 경로들에 로그인&특정한 Role이 있어야 접근 허용
        - hasAnyRole() : requestMatchers의 경로들에 로그인&여러가지 Role들에게 접근 허용
        - denyAll() : requestMatchers의 경로들에 모든 사용자들의 접근 거부
*/

        //다중 로그인 관리
        http.sessionManagement((auth) -> auth
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true));   //새 로그인 차단 -> Exception발생 가능성 있음

        //세션 설정 - statless상태로
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //JWT 인가 필터 추가 _로그인 필터 전에
        http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //로그인 필터 추가 _폼로그인 필터 자리에
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //CORS 허용 설정
        http.cors((corsCustomizer) -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration configuration = new CorsConfiguration();

                //허용할 프론트엔드 서버 포트번호
                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);

                //Authorization 헤더를 사용하므로 추가
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            }
        }));

        http.csrf((auth) -> auth.disable());

        http.formLogin((auth -> auth.disable()));

        http.httpBasic((auth) -> auth.disable());

        return http.build();		//return statement 마지막에 넣어줘야함
    }

    //로그인필터에서 사용하는 로그인 검증 객체
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    //패스워드 암호화 서비스 객체
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
