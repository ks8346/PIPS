package com.soprabanking.ips.services;

import java.util.UUID;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/** 
 * Email Service
 * This is a service class
 * this class is used to send the mail to the user containing the reset link
 * @author kavsharma
 * 
 * 
 */
@Service
public class EmailService {
	private static final Logger LOGGER = LogManager.getLogger(EmailService.class);
	

	/**
	* {@link JavaMailSender} object responsible for sending the mail to the registered email id.
	*/
	
	
	@Autowired
	private JavaMailSender sender;
	/** 
	 * This method sends the reset link to the registered email id.
	 * @param to: to of string type is a receiver mail id
	 * @param content: content of string type contains the content of the email
	 * @param subject: subject of string type contains the subject of the email
	 * 
	 */

	
	public void sendResetLink(String to, String content, String subject) {
		LOGGER.info("Inside EmailService : sendResetLink() method");
		
	
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setText(content);
		msg.setSubject(subject);
		sender.send(msg);
	}
	/** 
	 * This method returns the reset password link.
	 * @param id: id of uuid type is the token id.
	 * @return reset link
	 * 
	 */

	
	public String mailContent(UUID id) {
		LOGGER.info("Inside EmailService : mailContent() method");
		return "Password reset link " + "http://localhost:4200/resetLink/" + id;
	}

}
