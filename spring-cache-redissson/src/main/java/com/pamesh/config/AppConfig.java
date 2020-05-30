package com.pamesh.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * The Class AppConfig.
 *
 * @author Pamesh Bansal
 */
@Configuration
@ComponentScan(basePackages="com.pamesh")
public class AppConfig {

	/**
	 * Application properties.
	 *
	 * @return the application properties
	 */
	@Bean
	@Validated
	@ConfigurationProperties(prefix = "application")
	public ApplicationProperties applicationProperties() {
		return new ApplicationProperties();
	}
}
