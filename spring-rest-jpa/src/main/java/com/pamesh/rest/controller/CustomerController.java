package com.pamesh.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pamesh.rest.service.CustomerService;
import com.pamesh.rest.vo.CustomerVO;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public ResponseEntity<Void> saveCustomer(@Valid @RequestBody CustomerVO customerVO) {
		Long id = customerService.save(customerVO);
		return ResponseEntity.ok().header(HttpHeaders.LOCATION, "/customer/" + id).build();
	}

}
