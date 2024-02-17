package fr.ebiron.septunneuf.eggs.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                if (System.getenv("NODE_ENV")?.equals("DEVELOPMENT") == true) {
                    registry.addMapping("/**").allowedOrigins("*")
                }
            }
        }
    }
}