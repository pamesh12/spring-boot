package com.pamesh.rest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class Customer.
 *
 * @author Pamesh Bansal
 */
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Customer extends AbstractAuditingEntity{

	private static final long serialVersionUID = -8133248334114411124L;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;
	
	private String contactNumber;

	/** The addresses. */
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Set<Address> addresses = new HashSet<>();

}
