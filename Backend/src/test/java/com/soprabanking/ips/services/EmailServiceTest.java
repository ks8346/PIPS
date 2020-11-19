package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class EmailServiceTest {
	
	@MockBean
	private JavaMailSender sender;
	
	@Autowired
	private EmailService emailService;

	@Test
	void sendResetLinkSuccessfulltest() {
		
		Mockito.doNothing().when(sender).send(any(SimpleMailMessage.class));
		assertDoesNotThrow(()->emailService.sendResetLink("nk@gmail.com", "http://localhost:4200", "reset link"));
		verify(sender, times(1)).send(any(SimpleMailMessage.class));
		verifyNoMoreInteractions(sender);
	}
	
	@Test
	void sendResetLinkUnsuccessfulltest() {
		
		Mockito.doThrow(new MailSendException("")).when(sender).send(any(SimpleMailMessage.class));
		assertThrows(Exception.class, ()->emailService.sendResetLink("nk@gmail.com", "http://localhost:4200", "reset link"));
		verify(sender, times(1)).send(any(SimpleMailMessage.class));
		verifyNoMoreInteractions(sender);
	}
	
	@Test
	void mailContenttest() {
		
		UUID id = new UUID(2L, 2L);
		assertEquals("Password reset link " + "http://localhost:4200/resetLink/" + id, emailService.mailContent(id));
		verifyNoInteractions(sender);
	}

}
