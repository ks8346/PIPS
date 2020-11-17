package com.soprabanking.ips.controllers;


import java.util.TimerTask;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

 

import com.soprabanking.ips.helper.Email;
import com.soprabanking.ips.helper.Password;
import com.soprabanking.ips.helper.TokenId;
import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.services.EmailService;
import com.soprabanking.ips.services.TimerService;
import com.soprabanking.ips.services.TokenService;
import com.soprabanking.ips.services.UserService;

/**
 * ForgotPassword Controller
 * Provides Rest-APIs for forgot password and reset password.
 *
 * <p>
 * This is a ForgotPassword Controller Class which implements forget password handler(forgot_password rest-API), 
 * reset password handler (reset_password rest-API)
 * @author kavsharma
 */
@CrossOrigin
@RestController
public class ForgotPasswordController {
	
	private static final Logger LOGGER = LogManager.getLogger(ForgotPasswordController.class);
    
	@Autowired
    private TokenService tokenService;
    
    @Autowired
    private TimerService timerService;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;
    
    /**
	 * This method is used for verification of valid email id entered by the user, 
	 * generation of the token link and setting up the timer for the same.
	 * @param email registered email of the user who wants to reset the password.
	 * @return responseEntity which holds the response messages with their respective status codes.
	 * */
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody Email email) {
    	
    	LOGGER.info("Inside ForgotPasswordController : forgotPassword() method");

    	try {
    		if (userService.getUserByUsername(email.getMail()) == null) {
    			return new ResponseEntity<String>("Email does not exist!", HttpStatus.NOT_ACCEPTABLE);
    		}
    		try {
    			tokenService.deleteTokenByUsername(email.getMail());
    		}catch(Exception e) {}
    		Token token = tokenService.createToken(UUID.randomUUID(), email.getMail());
    		tokenService.saveToken(token);
    		String mail_content = emailService.mailContent(token.getId());
    		emailService.sendResetLink(email.getMail(), mail_content, "Forgot Password");
    		TimerTask task = timerService.createTimer(token.getId());
    		timerService.scheduleTimer(task, 60000L);
    		LOGGER.info("Inside ForgotPasswordController : forgotPassword() SUCCESS");
    		return new ResponseEntity<String>("message sent successfully", HttpStatus.OK);
    	}catch(Exception e) {
    		LOGGER.error("Inside ForgotPasswordControllerr :forgotPassword() FAILURE",e);
    		try {
    			tokenService.deleteTokenByUsername(email.getMail());
    		}catch(Exception e1) {}
    		return new ResponseEntity<String>("Failed to process the forgot password request", HttpStatus.NOT_ACCEPTABLE);
    	}
    	
    }
    /**
   	 * This method helps in validating the token whether the session is in continuation or expired 
   	 * @param id Id of the token
   	 * @return responseEntity which holds the response messages with their respective status codes.
   	 * */
    
    @PostMapping("validateToken")
    public ResponseEntity<String> validateToken(@RequestBody TokenId id) {

    	LOGGER.info("Inside ForgotPasswordController : validateToken() method");
    	try {
    		tokenService.findTokenById(id.getId()); 
    		LOGGER.info("Inside ForgotPasswordController : validateToken() SUCCESS");
    		return new ResponseEntity<String>("Validation Successfull", HttpStatus.ACCEPTED);
    	}catch(Exception e) {
    		LOGGER.error("Inside ForgotPasswordControllerr :validateToken() FAILURE",e);
    		return new ResponseEntity<String>("Validation Unsuccessfull", HttpStatus.NOT_ACCEPTABLE);
    	}
    	
    }
    /**
	 * This method helps in fetching the token from the database and sends an error if token is not present in the database.
	 * stores the updated password in the database
	 * @param password new password entered by the user
	 * @return responseEntity which holds the response messages with their respective status codes.
	 * */
    @PutMapping("resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Password password) {
    	LOGGER.info("Inside ForgotPasswordController : resetPassword() method");
    	try {
    		Token token = tokenService.findTokenById(password.getId());
    		userService.updatePassword(token.getEmail(), password.getPassword());
    		try {
    			tokenService.deleteTokenById(token.getId());
    		}catch(Exception e) {}
    		LOGGER.info("Inside ForgotPasswordController : resetPassword() SUCCESS");
    		return new ResponseEntity<String>("password updated", HttpStatus.OK);
    	}catch(Exception e) {
    		LOGGER.error("Inside ForgotPasswordController :resetPassword() FAILURE");
    		return new ResponseEntity<String>("password updation failed", HttpStatus.FAILED_DEPENDENCY);
    	}
    }
}
