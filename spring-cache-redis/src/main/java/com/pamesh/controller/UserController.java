package com.pamesh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pamesh.model.User;
import com.pamesh.service.UserService;

/**
 * The Class UserController.
 *
 * @author Pamesh Bansal
 */
@RestController
public class UserController {

	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Users.
	 *
	 * @return the response entity
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<User> user(@PathVariable Long id) {
		User user = userService.findUserById(id);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	/**
	 * Users.
	 *
	 * @return the response entity
	 */
	@PostMapping("/user/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Long id) {
		User user = userService.findUserById(id);
		if(user != null) {
			user.setFirstName("firstNameUpdated");
			user.setLastName("lastNameUpdated");
			userService.updateUser(user);
			return new ResponseEntity<>("User Updated successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("User does not exists", HttpStatus.BAD_REQUEST);
		
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		User user = userService.findUserById(id);
		if(user != null) {
			userService.deleteUser(user.getId());
			return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("User does not exists", HttpStatus.BAD_REQUEST);
		
	}
	
	public void saveUsers() {
		for(int i=0; i<4; i++) {
			User u = User.builder().firstName("firstName"+i).lastName("lastName"+i).build();
			userService.saveUser(u);
		}
	}
}
