
package com.soprabanking.ips.authentication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.soprabanking.ips.controllers.ForgotPasswordController;

/**
 * Returns the details of that user who attempting to login.
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

    public String getMessage() {
    	LOGGER.info("Inside AuthenticationBean : getMessage() method");
        return message;
    }

    public void setMessage(String message) {
    	LOGGER.info("Inside AuthenticationBean : setMessage() method");
        this.message = message;
    }

    @Override
    public String toString() {
        return (message);
    }
}
