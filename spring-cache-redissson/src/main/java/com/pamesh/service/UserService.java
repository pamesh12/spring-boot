package com.pamesh.service;

import com.pamesh.model.User;

/**
 * The Interface UserService.
 *
 * @author Pamesh Bansal
 */
public interface UserService {

	/**
	 * Save user.
	 *
	 * @param user the user
	 */
	void saveUser(User user);

	/**
	 * Find user by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	User findUserById(Long id);

	/**
	 * Delete user.
	 *
	 * @param id the id
	 */
	void deleteUser(Long id);

	/**
	 * Update user.
	 *
	 * @param id the id
	 */
	User updateUser(User user);

}
