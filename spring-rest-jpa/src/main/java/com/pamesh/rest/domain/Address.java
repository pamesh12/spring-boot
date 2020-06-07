package com.pamesh.rest.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class Address.
 *
 * @author Pamesh Bansal
 */
@Entity
@Table(name="address")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class Address extends AbstractAuditingEntity {

	private static final long serialVersionUID = 3672286615437735906L;

	/** The id. */
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Long id;
	
	/** The first line. */
	private String firstLine;
	
	/** The second line. */
	private String secondLine;
	
	@OneToOne(fetch = FetchType.EAGER, targetEntity = City.class)
	private City city;
	
	/** The state. */
	@OneToOne(fetch = FetchType.EAGER, targetEntity = State.class)
	private State state;
}
