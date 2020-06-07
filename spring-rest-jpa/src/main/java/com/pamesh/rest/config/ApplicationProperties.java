package com.pamesh.rest.config;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import com.pamesh.rest.vo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Properties are configured in the application.yml file.
 *
 * @author Pamesh Bansal
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties
@Slf4j
public class ApplicationProperties {

	/** The cors. */
	private CorsConfiguration cors = new CorsConfiguration();

	/** The async. */
	@Valid
	private final Async async = new Async();
	
	@NotBlank
	@Length(max = 4, min = 1)
	private String host;
	@NotNull
	private Integer port;
	
	@Valid
	private User user = new User();
	
	
	@PostConstruct
	public void logApplicationProperties() {
		log.info("{}", this);
	}

}
