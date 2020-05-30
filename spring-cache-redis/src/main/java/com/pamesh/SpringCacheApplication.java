package com.pamesh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pamesh.controller.UserController;

/**
 * The Class SpringCacheApplication.
 *
 * @author Pamesh Bansal
 */
@SpringBootApplication
public class SpringCacheApplication implements CommandLineRunner{

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringCacheApplication.class);
	
	@Autowired
	private UserController controller;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringCacheApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Creating users in DB");
		controller.saveUsers();
	}

}
