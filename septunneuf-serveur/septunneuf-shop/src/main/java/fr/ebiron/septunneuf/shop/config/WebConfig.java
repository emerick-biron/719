package fr.ebiron.septunneuf.shop.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    private final boolean disableCors;
    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    public WebConfig(@Value("#{environment['DISABLE_CORS']?:false}") boolean disableCors) {
        this.disableCors = disableCors;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                log.info("Cors disabled: {}", disableCors);
                if (disableCors) {
                    registry.addMapping("/**").allowedOrigins("*");
                }
            }
        };
    }
}
