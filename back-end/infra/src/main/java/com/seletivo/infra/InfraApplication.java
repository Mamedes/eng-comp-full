package com.seletivo.infra;

import com.seletivo.infra.configuration.RateLimitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
@EnableConfigurationProperties(RateLimitProperties.class)
public class InfraApplication {

	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "dev");
		SpringApplication.run(InfraApplication.class, args);
	}

}
