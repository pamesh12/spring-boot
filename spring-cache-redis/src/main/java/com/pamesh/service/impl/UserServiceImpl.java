package com.pamesh.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pamesh.model.User;
import com.pamesh.repository.UserRepository;
import com.pamesh.service.UserService;

/**
 * The Class UserServiceImpl.
 *
 * @author Pamesh Bansal
 */
@Service
public class UserServiceImpl implements UserService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void saveUser(User user) {
		LOGGER.info("Saving user {}", user);
		userRepository.save(user);
	}

	@Override
	@Cacheable(value = "usersCache", key="#id",  unless = "#result == null")
	public User findUserById(Long id) {
		LOGGER.info("Getting user with id {}", id);
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@CacheEvict(value = "usersCache", key = "#id")
	public void deleteUser(Long id) {
		LOGGER.info("Deleting user with id {}", id);
		userRepository.deleteById(id);
	}

	/**
	 * Update user.
	 *
	 * @param user the user
	 */
	@Override
	@CachePut(value = "usersCache", key = "#user.id")
	public User updateUser(User user) {
		LOGGER.info("Updating user with ID {}", user.getId());
		return userRepository.save(user);
	}

}
