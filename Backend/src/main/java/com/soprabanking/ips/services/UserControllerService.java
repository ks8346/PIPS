package com.soprabanking.ips.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;


@Service
public class UserControllerService {
	
	@Autowired
	private UserRepository userRepository;
    
	
	// fetching user object from database to check user exist in database or not
	public  User GetUserDetails(String username) {

		User user = userRepository.getUserByUserName(username);

		

		return user;

}
}
