package com.soprabanking.ips.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;
/** 
 * This is service class
 * this class is used for calling userRepository class to fetch the data from database
 * 
 */

@Service
public class UserControllerService {
	

 private static final Logger LOGGER = LogManager.getLogger(UserControllerService.class);
	@Autowired
	private UserRepository userRepository;
    
	/** 
	  * This method fetching user object from database to check user exist in database or not
	  * @param string  user name 
	  * @return user , object of user
	  * 
	  */
	 
	public  User GetUserDetails(String username) {
		LOGGER.info("Inside UserControllerService : GetUserDetails() method");

		User user = userRepository.getUserByUserName(username);

		

		return user;

}
}
