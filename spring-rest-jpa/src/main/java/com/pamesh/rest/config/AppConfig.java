package com.pamesh.rest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class AppConfig.
 *
 * @author Pamesh Bansal
 */
@Configuration
@Slf4j
public class AppConfig {

	/**
	 * Message source accessor.
	 *
	 * @param messageSource the message source
	 * @return the message source accessor
	 */
	@Bean
	public MessageSourceAccessor messageSourceAccessor(MessageSource messageSource) {
		return new MessageSourceAccessor(messageSource);
	}
	
	/**
	 * Cors filter.
	 *
	 * @param applicationProperties the application properties
	 * @return the cors filter
	 */
	@Bean
	public CorsFilter corsFilter(ApplicationProperties applicationProperties) {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = applicationProperties.getCors();
		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			log.debug("Registering CORS filter");
			source.registerCorsConfiguration(CorsConfiguration.ALL, config);
		}
		return new CorsFilter(source);
	}
	
	@Bean
	@ConfigurationProperties(prefix = "application")
	public ApplicationProperties applicationProperties() {
		return new ApplicationProperties();
	}
}
