package fr.ebiron.septunneuf.storage.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig(
    @Value("#{environment['CORS_ALL_ORIGINS']?:false}")
    private val corsAllOrigins: Boolean
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                log.info("Cors all origins: $corsAllOrigins")
                if (corsAllOrigins) {
                    registry.addMapping("/**").allowedOrigins("*")
                }
            }
        }
    }
}