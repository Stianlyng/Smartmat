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
 * @author Birk, Anders
 * @version 1.1
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
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(Arrays.asList("https://localhost",
                                domainProperty.domain()
                        ).toArray(String[]::new))
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
