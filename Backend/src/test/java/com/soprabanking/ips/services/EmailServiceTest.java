package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
	
	@Mock
	private JavaMailSender sender;
	
	@InjectMocks
	private EmailService emailService;

	@Test
	void sendResetLinkSuccessfulltest() {
		
		Mockito.doNothing().when(sender).send(any(SimpleMailMessage.class));
		assertDoesNotThrow(()->emailService.sendResetLink("nk@gmail.com", "http://localhost:4200", "reset link"));
	}
	
	@Test
	void sendResetLinkUnsuccessfulltest() {
		
		Mockito.doThrow(new MailSendException("")).when(sender).send(any(SimpleMailMessage.class));
		assertThrows(Exception.class, ()->emailService.sendResetLink("nk@gmail.com", "http://localhost:4200", "reset link"));
	}
	
	@Test
	void mailContenttest() {
		
		UUID id = new UUID(2L, 2L);
		assertEquals("Password reset link " + "http://localhost:4200/resetLink/" + id, emailService.mailContent(id));
	}

}
