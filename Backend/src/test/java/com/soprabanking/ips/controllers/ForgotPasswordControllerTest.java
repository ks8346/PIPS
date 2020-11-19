package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.TimerTask;
import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.helper.Email;
import com.soprabanking.ips.helper.Password;
import com.soprabanking.ips.helper.TokenId;
import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.services.EmailService;
import com.soprabanking.ips.services.TimerService;
import com.soprabanking.ips.services.TokenService;
import com.soprabanking.ips.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class ForgotPasswordControllerTest {
	private Email email;
	private TokenId tokenId;
	private Password password;
	private UUID id;
	private Token token;
	private TimerTask task;

	@MockBean
	private UserService userService;
	
	@MockBean
	private TokenService tokenService;
	
	@MockBean
	private TimerService timerService;
	
	@MockBean
	private EmailService emailService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
			public void run() {}
		};
	}
	
	@Test
	void forgotPasswordEmailNotExisttest() throws Exception {
		String request = objectMapper.writeValueAsString(email);
		when(userService.getUserDetails(email.getMail())).thenReturn(null);
		MvcResult result = mockMvc.perform(post("/forgotPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("Email does not exist!", result.getResponse().getContentAsString());
	}
	
	@Test
	void forgotPasswordSaveTokentest() throws Exception {
		String request = objectMapper.writeValueAsString(email);
		when(userService.getUserDetails(email.getMail())).thenReturn(new User());
		when(tokenService.deleteTokenByUsername(email.getMail())).thenReturn(true);
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(tokenService).saveToken(null);
		MvcResult result = mockMvc.perform(post("/forgotPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("Failed to process the forgot password request", result.getResponse().getContentAsString());
	}
	
	@Test
	void forgotPasswordSendMailtest() throws Exception {
		String request = objectMapper.writeValueAsString(email);
		when(userService.getUserDetails(email.getMail())).thenReturn(new User());
		when(tokenService.deleteTokenByUsername(email.getMail())).thenReturn(true);
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doThrow(new MailSendException("")).when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		MvcResult result = mockMvc.perform(post("/forgotPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("Failed to process the forgot password request", result.getResponse().getContentAsString());
	}
	
	@Test
	void forgotPasswordTimerScheduletest() throws Exception {
		String request = objectMapper.writeValueAsString(email);
		when(userService.getUserDetails(email.getMail())).thenReturn(new User());
		when(tokenService.deleteTokenByUsername(email.getMail())).thenReturn(true);
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doNothing().when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		when(timerService.createTimer(id)).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(timerService).scheduleTimer(null, 300000L);
		MvcResult result = mockMvc.perform(post("/forgotPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("Failed to process the forgot password request", result.getResponse().getContentAsString());
	}
	
	@Test
	void forgotPasswordtest() throws Exception {
		String request = objectMapper.writeValueAsString(email);
		when(userService.getUserDetails(email.getMail())).thenReturn(new User());
		when(tokenService.deleteTokenByUsername(email.getMail())).thenReturn(true);
		when(tokenService.createToken(any(UUID.class), anyString())).thenReturn(token);
		Mockito.doNothing().when(tokenService).saveToken(token);
		when(emailService.mailContent(id)).thenReturn("");
		Mockito.doNothing().when(emailService).sendResetLink(email.getMail(), "", "Forgot Password");
		when(timerService.createTimer(id)).thenReturn(task);
		Mockito.doNothing().when(timerService).scheduleTimer(task, 300000L);
		MvcResult result = mockMvc.perform(post("/forgotPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals("message sent successfully", result.getResponse().getContentAsString());
	}
	
	@Test
	void validateTokenSuccessfullTest() throws Exception {
		String request = objectMapper.writeValueAsString(tokenId);
		when(tokenService.findTokenById(id)).thenReturn(token);
		MvcResult result = mockMvc.perform(post("/validateToken")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals("Validation Successfull", result.getResponse().getContentAsString());
	}
	
	@Test
	void validateTokenUnsuccessfullTest() throws Exception {
		String request = objectMapper.writeValueAsString(tokenId);
		when(tokenService.findTokenById(id)).thenThrow(EntityNotFoundException.class);
		MvcResult result = mockMvc.perform(post("/validateToken")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("Validation Unsuccessfull", result.getResponse().getContentAsString());
	}
	
	@Test
	void resetPasswordFindTokenTest() throws Exception {
		String request = objectMapper.writeValueAsString(password);
		when(tokenService.findTokenById(id)).thenThrow(EntityNotFoundException.class);
		MvcResult result = mockMvc.perform(put("/resetPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("password updation failed", result.getResponse().getContentAsString());
	}
	
	@Test
	void resetPasswordUpdatePasswordTest() throws Exception {
		String request = objectMapper.writeValueAsString(password);
		when(tokenService.findTokenById(id)).thenReturn(token);
		Mockito.doThrow(new IllegalArgumentException()).when(userService).updatePassword(token.getEmail(), password.getPassword());
		MvcResult result = mockMvc.perform(put("/resetPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		assertEquals("password updation failed", result.getResponse().getContentAsString());
	}
	
	@Test
	void resetPasswordTest() throws Exception {
		String request = objectMapper.writeValueAsString(password);
		when(tokenService.findTokenById(id)).thenReturn(token);
		Mockito.doNothing().when(userService).updatePassword(token.getEmail(), password.getPassword());
		when(tokenService.deleteTokenById(id)).thenReturn(true);
		MvcResult result = mockMvc.perform(put("/resetPassword")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals("password updated", result.getResponse().getContentAsString());
	}

}
