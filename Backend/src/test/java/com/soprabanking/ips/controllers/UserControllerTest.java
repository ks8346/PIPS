package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.helper.UserAuth;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.services.UserControllerService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	private UserAuth userAuth;
	private User user;

	@Mock
	private UserControllerService userControllerService;
	
	@Mock
	private ObjectMapper mapper;
	
	@InjectMocks
	private UserController userController;
	
	@BeforeEach
	void setUp() throws Exception {
		
		userAuth = new UserAuth();
		userAuth.setName("nishu");
		userAuth.setEmail("nk@gmail.com");
		
		user = new User();
		user.setId(1L);
		user.setEmail("nk@gmail.com");
		user.setName("nishu");
		user.setPassword("password");
	}

	@Test
	void getSocialInfoUserIsNulltest() {
		
		when(userControllerService.getUserDetails("nk@gmail.com")).thenReturn(null);
		
		assertTrue(userController.getSocialInfo(userAuth).getBody().equals(userAuth));
		
	}
	
	@Test
	void getSocialInfoSuccessfulltest() {
		
		when(userControllerService.getUserDetails("nk@gmail.com")).thenReturn(user);
		
		assertEquals(HttpStatus.OK, userController.getSocialInfo(userAuth).getStatusCode());
		
	}
	
	@Test
	void getSocialInfoUnsuccessfulltest() throws JsonProcessingException {
		
		when(userControllerService.getUserDetails("nk@gmail.com")).thenReturn(user);
		when(mapper.writeValueAsString(any(User.class))).thenThrow(JsonProcessingException.class);
		assertEquals(HttpStatus.UNAUTHORIZED, userController.getSocialInfo(userAuth).getStatusCode());
		
	}

}
