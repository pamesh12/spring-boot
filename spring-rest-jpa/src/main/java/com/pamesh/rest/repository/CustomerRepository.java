package com.pamesh.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pamesh.rest.domain.Customer;

/**
 * The Interface CustomerRepository.
 *
 * @author Pamesh Bansal
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
