package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.authentication.AuthenticationBean;
import com.soprabanking.ips.helper.UserAuth;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	
	private UserAuth userAuth;
	private User user;

	@MockBean
	private UserService userService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
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
	void getSocialInfoUserIsNulltest() throws Exception {
		String request = objectMapper.writeValueAsString(userAuth);
		when(userService.getUserDetails("nk@gmail.com")).thenReturn(null);
		MvcResult result = mockMvc.perform(post("/getSocialInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is4xxClientError())
				.andReturn();
		UserAuth actualData = objectMapper.readValue(result.getResponse().getContentAsString(), UserAuth.class);
		assertEquals(userAuth.getEmail(), actualData.getEmail());
		
	}
	
	@Test
	void getSocialInfoSuccessfulltest() throws Exception {
		String request = objectMapper.writeValueAsString(userAuth);
		when(userService.getUserDetails("nk@gmail.com")).thenReturn(user);
		MvcResult result = mockMvc.perform(post("/getSocialInfo")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals(200, result.getResponse().getStatus());
		
	}

}
