package com.soprabanking.ips.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.authentication.AuthenticationBean;
import com.soprabanking.ips.helper.UserAuth;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.services.UserService;

/**
 * User Controller
 * Provides Rest-APIs for logging in through social media .
 *
 * <p>
 * This is a User Controller Class which implements social media handler(getSocialInfo rest API) and with this handler, 
 * user can access our landing page via social media(GMAIL).
 * @author kavsharma
 */
@CrossOrigin
@RestController

public class UserController
{

	private static final Logger LOGGER = LogManager.getLogger(UserController.class);  
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;
	/**
	 * This method verifies that the user exists in our database or not.
	 * if the user already exists then it returns all the information of that user and redirects to landing page.
	 * else user redirects to create team page for registration.
	 * @param  userAuth the userAuth has the information of that user who logged in via social media.
	 * @return ResponseEntity with HTTP Status .
	 * 
	 * */

	@PostMapping("/getSocialInfo")
	@ResponseBody
	public  ResponseEntity getSocialInfo(@RequestBody UserAuth userAuth)

	{
		LOGGER.info("Inside  UserController : getSocialInfo() method");
		String userName=userAuth.getEmail();
		User user1 = userService.getUserDetails(userName);
		if(user1==null)
		{
			LOGGER.info("Inside UserController : getSocialInfo() SUCCESS");
			return new ResponseEntity(userAuth,HttpStatus.NOT_FOUND);
		}
		else 
		{
			try {
				String s = objectMapper.writeValueAsString(user1);
				LOGGER.info("Inside UserController : getSocialInfo() SUCCESS");
				return new ResponseEntity(new AuthenticationBean(s), HttpStatus.OK);

			} catch (Exception e) {
				LOGGER.error("Inside  UserController :getSocialInfo() FAILURE");
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		}
	}
}




