package ntnu.idatt2016.v233.SmartMat.config;

import lombok.AllArgsConstructor;
import ntnu.idatt2016.v233.SmartMat.config.properties.DomainProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * Cors configuration for application
 * @author Birk
 * @version 1.0
 * @since 04.04.2023
 */
@Configuration
@AllArgsConstructor
public class CorsConfig {
    /**
     * The domain property.
     */
    private final DomainProperty domainProperty;

    /**
     * Configures CORS for the application.
     * @return {@link WebMvcConfigurer} with CORS configuration
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * TODO: Change allowedOrigins setup (Can be done in application.properties?)
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(domainProperty.domain())
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods(Arrays.asList(
                                HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PUT.name(),
                                HttpMethod.DELETE.name(),
                                HttpMethod.OPTIONS.name()
                        ).toArray(String[]::new))
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
