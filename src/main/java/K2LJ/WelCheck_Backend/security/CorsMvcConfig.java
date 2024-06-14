package K2LJ.WelCheck_Backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //모든 컨트롤러의 경로에 대해서, 프론트엔드 서버의 포트번호에 대해 cors 허용
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000");
    }
}
