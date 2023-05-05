package ntnu.idatt2016.v233.SmartMat.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the domain of the application
 * @author Birk
 * @version 1.0
 */
@ConfigurationProperties(prefix = "domain")
public record DomainProperty (String domain){
}
