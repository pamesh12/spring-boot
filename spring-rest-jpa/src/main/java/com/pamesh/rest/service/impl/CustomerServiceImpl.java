package com.pamesh.rest.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pamesh.rest.domain.Address;
import com.pamesh.rest.domain.Customer;
import com.pamesh.rest.repository.CustomerRepository;
import com.pamesh.rest.service.CustomerService;
import com.pamesh.rest.vo.CustomerVO;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class CustomerServiceImpl.
 *
 * @author Pamesh Bansal
 */
@Service
@Slf4j 
public class CustomerServiceImpl implements CustomerService {

	/** The customer repository. */
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Save.
	 *
	 * @param vo the vo
	 */
	@Override
	public Long save(CustomerVO vo) {
		log.info("Saving customer record {}", vo);
		
		Address address = Address.builder().firstLine(vo.getAddFirstLine())
				.secondLine(vo.getAddSecondLine()).build();
		
		Customer customer = Customer.builder().firstName(vo.getFirstName()).lastName(vo.getLastName())
				.contactNumber(vo.getContactNumber()).addresses(Collections.singleton(address)).build();
		
		Customer savedCustomer = customerRepository.save(customer);
		return savedCustomer.getId();
	}
}
