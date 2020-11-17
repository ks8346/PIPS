package com.soprabanking.ips.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;
/**
 * This class is a Data Access Object Class for <a href="User.html">{@link com.soprabanking.ips.models.User}</a> in the system.It has the getById method
 * @author mojha
 * @see org.springframework.stereotype.Component
 */
@Component
public class UserDAO {
    @Autowired
    private UserRepository userRepository;

    /**
     * This method is used to get user by the user id.<br>
     * Variables used
     * <br>1)userRepository : an Object of the <a href="UserRepository.html">{@link com.soprabanking.ips.repositories.UserRepository}</a>
     * @param id : User Id
     * @return User
     */
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
