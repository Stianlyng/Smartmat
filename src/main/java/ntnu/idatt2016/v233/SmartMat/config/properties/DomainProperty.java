package ntnu.idatt2016.v233.SmartMat.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "domain")
public record DomainProperty (String domain){
}
