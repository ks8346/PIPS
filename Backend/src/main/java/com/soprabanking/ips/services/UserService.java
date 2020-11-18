package com.soprabanking.ips.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.User;

/** 
 * User Service
 * This is service class
 * this class is used for storing the updated password into the database and fetching user by username from the database
 * <p> Service methods invokes the respective methods of {@link UserDAO},{@link PasswordEncoder} in order to update the password</p>
 * @author kavsharma
 * @see com.soprabanking.ips.daos.UserDAO 
 */
@Service
public class UserService {
	
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private PasswordEncoder encoder;

	/**
	 * Called for storing the updated encoded password for the corresponding username 
	 * in the repository.
	 * @param username: String username
	 * @param password: String password of the user
	 */
	public void updatePassword(String username, String password) {
		
		LOGGER.info("Inside UserService : updatePassword(username,password) method");
		userDao.updatePassword(username, encoder.encode(password));
	}
	
	/**
	 * Returns a {@link User} object for the respective username provided. 
	 * @param username: String username
	 * @return A User object
	 */
	public User getUserByUsername(String username) {
		LOGGER.info("Inside UserService : getUserByUsername(username) method");
		return userDao.getUser(username);
	}

}
