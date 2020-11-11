package com.soprabanking.ips.controllers;

 

import java.util.Timer;

import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

 

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
import com.soprabanking.ips.services.DeleteTokenService;

/**
 * Provides Rest-APIs for forgot password and reset password.
 *
 * <p>
 * This is a ForgotPassword Controller Class which implements forget password handler(forgot_password rest-API), 
 * reset password handler (reset_password rest-API)
 * 
 */
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
    private DeleteTokenService delete_token_service;
    
    @Autowired
    private ReentrantLock lock;
    
    @Autowired
    private BCryptPasswordEncoder encoder;
    
    /**
	 * This method is used for verification of valid email id entered by the user, 
	 * generation of the token link and setting up the timer for the same.
	 * @param email registered email of the user who wants to reset the password.
	 * @return responseEntity which holds the response messages with their respective status codes.
	 * */
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
        
        //deleting token if existed
        Token old_token = token_repo.getTokenByEmail(email.getMail());
        if (old_token != null) {
            lock.lock();
            delete_token_service.deleteToken(old_token.getId());
            lock.unlock();
        }
        
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
                lock.lock();
                delete_token_service.deleteToken(token.getId());
                lock.unlock();
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
        
        return new ResponseEntity<String>("Validation Successfull", HttpStatus.ACCEPTED);
    }
    /**
	 * This method helps in validating the token whether the session is in continuation or expired 
	 * stores the updated password in the database
	 * @param password new password entered by the user
	 * @return responseEntity which holds the response messages with their respective status codes.
	 * */
    @PutMapping("reset_password")
    public ResponseEntity<String> reset_password(@RequestBody Password password) {
        
        lock.lock();
        //validating token
        Token token = token_repo.findById(password.getId()).orElse(null);
        if (token == null) {
            return new ResponseEntity<String>("session timeout", HttpStatus.NOT_ACCEPTABLE);
        }
        
        //updating password
        user_repo.updatePassword(token.getEmail(), encoder.encode(password.getPassword()));
        
        delete_token_service.deleteToken(token.getId());
        
        lock.unlock();
        
        return new ResponseEntity<String>("password updated", HttpStatus.OK);
        
    }

 

}
