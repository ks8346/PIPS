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
    /**
     * This method is used to get user by the user name.<br>
     * Variables used
     * <br>1)userRepository : an Object of the <a href="UserRepository.html">{@link com.soprabanking.ips.repositories.UserRepository}</a>
     * @param username: username of string type
     * @return User
     */
    
    public User getUser(String username) {
    	return userRepository.getUserByUserName(username);
    }
    /**
     * This method is used to update the password.<br>
     * Variables used
     * <br>1)userRepository : an Object of the <a href="UserRepository.html">{@link com.soprabanking.ips.repositories.UserRepository}</a>
     * @param username : username of string type
     * @param password : user password of string type
     
     */

    public void updatePassword(String username, String password) {
    	
    	if (userRepository.updatePassword(username, password) == 0) {
    		throw new IllegalArgumentException();
    	}
    }
    /**
     * This method is used to get user details.<br>
     * Variables used
     * <br>1)userRepository : an Object of the <a href="UserRepository.html">{@link com.soprabanking.ips.repositories.UserRepository}</a>
     * @param username : Username of string type
     * @return User
     */
    public  User getUserDetailsdao(String username) {
        
        User user = userRepository.getUserByUserName(username);

 
        return user;

    }
    /**
     * This method is used to save the user.<br>
     * Variables used
     * <br>1)userRepository : an Object of the <a href="UserRepository.html">{@link com.soprabanking.ips.repositories.UserRepository}</a>
     * @param user: an object of the user
     */
    public void saveUserdao(User user) {
    
        userRepository.save(user);
    }
}
