package com.pamesh.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.pamesh.rest.vo.CustomerVO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringRestJpaApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void saveCustomer() {
		CustomerVO vo = CustomerVO.builder().firstName("pamesh").lastName("bansal").contactNumber("1234567890")
				.build();
		this.restTemplate.postForEntity("http://localhost:" + port + "/customer", vo, ResponseEntity.class);
	}

}
