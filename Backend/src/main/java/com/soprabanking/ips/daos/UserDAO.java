package com.soprabanking.ips.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;

@Component
public class UserDAO {
    @Autowired
    private UserRepository userRepository;

    public User getById(Long id) {
        return userRepository.getOne(id);
    }
    
    public User getUser(String username) {
    	return userRepository.getUserByUserName(username);
    }

    public void updatePassword(String username, String password) {
    	
    	if (userRepository.updatePassword(username, password) == 0) {
    		throw new IllegalArgumentException();
    	}
    }
}
