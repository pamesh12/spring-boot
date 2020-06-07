package com.pamesh.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pamesh.rest.service.CustomerService;
import com.pamesh.rest.vo.CustomerVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public ResponseEntity<Void> saveCustomer(@Valid @RequestBody CustomerVO customerVO) {
		log.info("Saving customer {}", customerVO);
		Long id = customerService.save(customerVO);
		return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/customer/" + id).build();
	}
	
	
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerVO>> getCustomers() {
		log.info("Getting all customers ");
		List<CustomerVO> customers = customerService.getCustomers();
		return ResponseEntity.ok().body(customers);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<CustomerVO> getCustomers(@PathVariable Long id) {
		log.info("Getting customer with id {} ", id);
		CustomerVO customer = customerService.getCustomers(id);
		return ResponseEntity.ok().body(customer);
	}
}
