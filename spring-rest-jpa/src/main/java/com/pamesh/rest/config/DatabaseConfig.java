package com.pamesh.rest.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The Class DatabaseConfig.
 *
 * @author Pamesh Bansal
 */
@Configuration
@EnableJpaRepositories("com.pamesh.rest.repository")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableTransactionManagement
@EntityScan("com.pamesh.rest.domain")
public class DatabaseConfig {

}
