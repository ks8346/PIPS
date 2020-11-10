package com.soprabanking.ips.controllers;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.helper.Email;
import com.soprabanking.ips.helper.Password;
import com.soprabanking.ips.helper.TokenId;
import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.repositories.TokenRepository;
import com.soprabanking.ips.repositories.UserRepository;

@CrossOrigin
@RestController
public class ForgotPasswordController {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private TokenRepository token_repo;
	
	@Autowired
	private UserRepository user_repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@PostMapping("/forgot_password")
	public ResponseEntity<String> forgot_password(@RequestBody Email email) {
		
		//email validation
		if (user_repo.getUserByUserName(email.getMail()) == null) {
			return new ResponseEntity<String>("Email does not exist!", HttpStatus.NOT_ACCEPTABLE);
		}
		
		//token creation
		Token token = new Token();
		token.setId(UUID.randomUUID());
		token.setEmail(email.getMail());
		
		//saving token in database
		token_repo.save(token);
		
		String mail_content = "Password reset link " + "http://localhost:4200/resetLink/" + token.getId();
		
		//message creation
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email.getMail());
		msg.setText(mail_content);
		msg.setSubject("Forgot Password");
		
		//sending reset password link through mail
		sender.send(msg);
		
		//creating and setting timer
		TimerTask task = new TimerTask() {
			public void run() {
				token_repo.deleteById(token.getId());
			}
		};
		
		Timer timer = new Timer("timer");
		timer.schedule(task, 300000L);
		
		//System.out.println(mail_content);
		
		return new ResponseEntity<String>("message sent successfully", HttpStatus.OK);
	}
	
	@PostMapping("validate_token")
	public ResponseEntity<String> validate_token(@RequestBody TokenId id) {
		
		//validating token
		Token token = token_repo.findById(id.getId()).orElse(null);
		if (token == null) {
			return new ResponseEntity<String>("session timeout", HttpStatus.NOT_ACCEPTABLE);
		}
		
		/*else if (current.getYear() == token.getExpiry_date_time().getYear()
			&& current.getMonth() == token.getExpiry_date_time().getMonth()
			&& current.getDayOfMonth() == token.getExpiry_date_time().getDayOfMonth()
			&& current.getHour() == token.getExpiry_date_time().getHour()
			&& (current.getMinute() - token.getExpiry_date_time().getMinute()) <= 10) {
			
			return new ResponseEntity<String>("Validation Successfull", HttpStatus.ACCEPTED);
		}
		
		else {
			return new ResponseEntity<String>("Validation Unsuccessfull", HttpStatus.NOT_ACCEPTABLE);
		}*/
		
		return new ResponseEntity<String>("Validation Successfull", HttpStatus.ACCEPTED);
	}
	
	@PutMapping("reset_password")
	public ResponseEntity<String> reset_password(@RequestBody Password password) {
		
		//validating token
		Token token = token_repo.findById(password.getId()).orElse(null);
		if (token == null) {
			return new ResponseEntity<String>("session timeout", HttpStatus.NOT_ACCEPTABLE);
		}
		
		//updating password
		user_repo.updatePassword(token.getEmail(), encoder.encode(password.getPassword()));
		
		return new ResponseEntity<String>("password updated", HttpStatus.OK);
		
	}

}
