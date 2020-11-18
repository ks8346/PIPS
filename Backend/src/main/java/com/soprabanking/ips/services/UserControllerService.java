package com.soprabanking.ips.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;
/** 
 * User Controller Service
 * This is service class
 * this class is used for calling userRepository class to fetch the data from database
 * <p> Service methods invokes the respective methods of {@link UserDAO},{@link TeamDAO} in order to return the required results to the {@link com.soprabanking.ips.controllers.UserController}</p>
 * @author kavsharma
 * @see com.soprabanking.ips.models.User
 * @see com.soprabanking.ips.daos.UserDAO
 * @see com.soprabanking.ips.daos.TeamDAO
 * @see com.soprabanking.ips.controllers.HomeController
 * @see com.soprabanking.ips.controllers.UserController 
 */

@Service
public class UserControllerService {
	

 private static final Logger LOGGER = LogManager.getLogger(UserControllerService.class);
 /**
 * {@link UserDAO} object responsible for fetching the data of the users, saving the user details, updating the password and fetching the user id {@link User} objects by interacting with the persistence layer 
 */
 	
 @Autowired
 private UserDAO userDao;
 
 /** 
   * This method fetches user object from database to check user exists in database or not
   * @param username:username of string type that conatins the name of the user 
   * @return user:An object of user
   * 
   */
  
 public  User getUserDetails(String username) {
     LOGGER.info("Inside UserControllerService : GetUserDetails() method");

     User user = userDao.getUser(username);


     return user;

 }
 /** 
  * This method is used to save the user details in the database.
  * @param user: An Object of the <a href="User.html">{@link com.soprabanking.ips.models.User}</a>
  * 
  */
 
 public void saveUser(User user) {
     
     LOGGER.info("Inside UserControllerService : saveUser() method");
     userDao.saveUserdao(user);
 }
}