
package com.soprabanking.ips.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soprabanking.ips.controllers.ForgotPasswordController;

/**
 * Authentication Bean
 * Returns the details of that user who attempting to login.
 * @author kavsharma
 *
 * 
 * 
 */

public class AuthenticationBean {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticationBean.class);
    private String message;

    public AuthenticationBean(String message) {
    	LOGGER.info("Inside AuthenticationBean : AuthenticationBean() method");
        this.message = message;
    }
    
    /**
     * Getter method for retrieving the message containing user info.
     * @return a String text message
     */
    public String getMessage() {
    	LOGGER.info("Inside AuthenticationBean : getMessage() method");
        return message;
    }
    
    /**
     * This method sets the message containing user info.
     * @param message: A String text of user info
     */
    
    public void setMessage(String message) {
    	LOGGER.info("Inside AuthenticationBean : setMessage() method");
        this.message = message;
    }

    @Override
    public String toString() {
        return (message);
    }
}
