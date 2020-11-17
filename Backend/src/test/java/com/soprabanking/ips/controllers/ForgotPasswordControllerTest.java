package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.TimerTask;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;

import com.soprabanking.ips.helper.Email;
import com.soprabanking.ips.helper.Password;
import com.soprabanking.ips.helper.TokenId;
import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.services.EmailService;
import com.soprabanking.ips.services.TimerService;
import com.soprabanking.ips.services.TokenService;
import com.soprabanking.ips.services.UserService;

@ExtendWith(MockitoExtension.class)
class ForgotPasswordControllerTest {
	private Email email;
	private TokenId tokenId;
	private Password password;
	private UUID id;
	private Token token;
	private TimerTask task;

	@Mock
	private UserService userService;
	
	@Mock
	private TokenService tokenService;
	
	@Mock
	private TimerService timerService;
	
	@Mock
	private EmailService emailService;
	
	@InjectMocks
	private ForgotPasswordController forgotPasswordController;
	
	@BeforeEach
	void init() {
		id = new UUID(1L, 1L);
		
		email = new Email();
		email.setMail("nk@gmail.com");
		
		tokenId = new TokenId();
		tokenId.setId(id);
		
		password = new Password();
		password.setId(id);
		password.setPassword("password");
		
		token = new Token();
		token.setEmail(email.getMail());
		token.setId(id);
		
		task = new TimerTask() {
			public void run() {
				
			}
		};
	}
	
	@Test
	void forgotPasswordEmailNotExisttest() {
		
		when(userService.getUserByUsername(email.getMail())).thenReturn(null);
		assertEquals("Email does not exist!", forgotPasswordController.forgotPassword(email).getBody());
	}
	
	@Test
	void forgotPasswordSaveTokentest() {
		
		when(userService.getUserByUsername(email.getMail())).thenReturn(new User());
		Mockito.doNothing().when(tokenService).deleteTokenByUsername(email.getMail());
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(tokenService).saveToken(null);
		assertEquals("Failed to process the forgot password request", forgotPasswordController.forgotPassword(email).getBody());
	}
	
	@Test
	void forgotPasswordSendMailtest() {
		
		when(userService.getUserByUsername(email.getMail())).thenReturn(new User());
		Mockito.doThrow(new IllegalArgumentException()).when(tokenService).deleteTokenByUsername(email.getMail());
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doThrow(new MailSendException("")).when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		assertEquals("Failed to process the forgot password request", forgotPasswordController.forgotPassword(email).getBody());
	}
	
	@Test
	void forgotPasswordTimerScheduletest() {
		
		when(userService.getUserByUsername(email.getMail())).thenReturn(new User());
		Mockito.doNothing().when(tokenService).deleteTokenByUsername(email.getMail());
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doNothing().when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		when(timerService.createTimer(id)).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(timerService).scheduleTimer(null, 60000L);
		assertEquals("Failed to process the forgot password request", forgotPasswordController.forgotPassword(email).getBody());
	}
	
	@Test
	void forgotPasswordtest() {
		
		when(userService.getUserByUsername(email.getMail())).thenReturn(new User());
		Mockito.doNothing().when(tokenService).deleteTokenByUsername(email.getMail());
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doNothing().when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		when(timerService.createTimer(id)).thenReturn(task);
		Mockito.doNothing().when(timerService).scheduleTimer(task, 60000L);
		assertEquals("message sent successfully", forgotPasswordController.forgotPassword(email).getBody());
	}
	
	@Test
	void validateTokenSuccessfullTest() {
		
		when(tokenService.findTokenById(id)).thenReturn(new Token());
		
		assertEquals("Validation Successfull", forgotPasswordController.validateToken(tokenId).getBody());
	}
	
	@Test
	void validateTokenUnsuccessfullTest() {
		
		when(tokenService.findTokenById(id)).thenThrow(EntityNotFoundException.class);
		
		assertEquals("Validation Unsuccessfull", forgotPasswordController.validateToken(tokenId).getBody());
	}
	
	@Test
	void resetPasswordFindTokenTest() {
		
		when(tokenService.findTokenById(id)).thenThrow(EntityNotFoundException.class);
		
		assertEquals("password updation failed", forgotPasswordController.resetPassword(password).getBody());
	}
	
	@Test
	void resetPasswordUpdatePasswordTest() {
		
		when(tokenService.findTokenById(id)).thenReturn(token);
		Mockito.doThrow(new IllegalArgumentException()).when(userService).updatePassword(token.getEmail(), password.getPassword());
		
		assertEquals("password updation failed", forgotPasswordController.resetPassword(password).getBody());
	}
	
	@Test
	void resetPasswordTest1() {
		
		when(tokenService.findTokenById(id)).thenReturn(token);
		Mockito.doNothing().when(userService).updatePassword(token.getEmail(), password.getPassword());
		Mockito.doNothing().when(tokenService).deleteTokenById(id);
		
		assertEquals("password updated", forgotPasswordController.resetPassword(password).getBody());
	}
	
	@Test
	void resetPasswordTest2() {
		
		when(tokenService.findTokenById(id)).thenReturn(token);
		Mockito.doNothing().when(userService).updatePassword(token.getEmail(), password.getPassword());
		Mockito.doThrow(new IllegalArgumentException()).when(tokenService).deleteTokenById(id);
		
		assertEquals("password updated", forgotPasswordController.resetPassword(password).getBody());
	}

}
