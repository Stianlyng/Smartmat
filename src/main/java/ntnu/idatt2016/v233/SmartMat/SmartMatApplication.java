package ntnu.idatt2016.v233.SmartMat;

import ntnu.idatt2016.v233.SmartMat.config.properties.DomainProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({DomainProperty.class})
public class SmartMatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartMatApplication.class, args);
	}

}
