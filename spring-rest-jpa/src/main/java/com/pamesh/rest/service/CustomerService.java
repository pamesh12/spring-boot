package com.pamesh.rest.service;

import java.util.List;

import com.pamesh.rest.vo.CustomerVO;

/**
 * The Interface CustomerService.
 *
 * @author Pamesh Bansal
 */
public interface CustomerService {

	/**
	 * Save.
	 *
	 * @param vo the vo
	 */
	Long save(CustomerVO vo);
	
	
	List<CustomerVO> getCustomers();
	
	CustomerVO getCustomers(Long id);

}
