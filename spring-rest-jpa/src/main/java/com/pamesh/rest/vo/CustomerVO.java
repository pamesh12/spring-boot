package com.pamesh.rest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerVO {

	/** The first name. */
	private String firstName;

	/** The last name. */
	private String lastName;
	
	private String contactNumber;
	
	/** The first line. */
	private String addFirstLine;
	
	/** The second line. */
	private String addSecondLine;
	
	private Long cityId;
	
	private Long stateId;
}
