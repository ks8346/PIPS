package com.soprabanking.ips.daos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);
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
        LOGGER.info("Inside UserDAO : getById() method");
        return userRepository.getOne(id);
    }

}
