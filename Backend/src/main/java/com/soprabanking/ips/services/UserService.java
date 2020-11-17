package com.soprabanking.ips.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.User;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private PasswordEncoder encoder;

	public void updatePassword(String username, String password) {

		userDao.updatePassword(username, encoder.encode(password));
	}

	public User getUserByUsername(String username) {

		return userDao.getUser(username);
	}

}
