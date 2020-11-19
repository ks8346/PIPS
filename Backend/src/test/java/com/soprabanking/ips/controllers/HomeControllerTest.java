package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.*;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.modelwrap.ModelWrap;
import com.soprabanking.ips.services.HomeService;
import com.soprabanking.ips.services.UserService;


@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {
	
	private ModelWrap modelWrap;
	private User user;
	private Team team;

	@MockBean
	private UserService userService;
	
	@MockBean
	private HomeService homeService;
	
	@MockBean
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		
		team = new Team();
		team.setId(1L);
		team.setName("sparks");
		
		user = new User();
		user.setId(1L);
		user.setEmail("nk@gmail.com");
		user.setName("nishu");
		user.setPassword("password");
		
		modelWrap = new ModelWrap();
		modelWrap.setTeam(team);
		modelWrap.setUser(user);
		
	}

	@Test
	void hometest() throws Exception {
		
		MvcResult result = mockMvc.perform(get("/home"))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals("this is home page of IPS", result.getResponse().getContentAsString());
	}
	
	@Test
	void findAllTeamstest1() throws Exception {
		
		List<Object> teams = new ArrayList<>();
		Team team1 = new Team();
		team1.setId(1L);
		team1.setName("sparks");
		
		Team team2 = new Team();
		team2.setId(1L);
		team2.setName("sparks");
		
		teams.add(team1);
		teams.add(team2);
		
		when(homeService.getTeam()).thenReturn(teams);
		MvcResult result = mockMvc.perform(get("/getTeam"))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		List<Object> actualTeams = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Object[].class));
		assertEquals(2, actualTeams.size());
	}
	
	@Test
	void findAllTeamstest2() throws Exception {
		
		when(homeService.getTeam()).thenThrow(new IllegalStateException());
		MvcResult result = mockMvc.perform(get("/getTeam"))
				.andExpect(status().is4xxClientError())
				.andReturn();
		List<Object> actualTeams = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Object[].class));
		assertTrue(actualTeams.isEmpty());
	}
	
	@Test
	void registerUsertest1() throws Exception {
		String request = objectMapper.writeValueAsString(modelWrap);
		when(passwordEncoder.encode(anyString())).thenReturn("password");
		when(homeService.getTeamname(team.getName())).thenReturn(team);
		Mockito.doNothing().when(userService).saveUser(any(User.class));
		MvcResult result = mockMvc.perform(post("/userRegister")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		assertEquals(200, result.getResponse().getStatus());
	}
	
	@Test
	void registerUsertest2() throws Exception {
		String request = objectMapper.writeValueAsString(modelWrap);
		when(passwordEncoder.encode(anyString())).thenReturn("password");
		when(homeService.getTeamname(team.getName())).thenReturn(null);
		Mockito.doNothing().when(userService).saveUser(any(User.class));
		MvcResult result = mockMvc.perform(post("/userRegister")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is2xxSuccessful())
				.andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		
	}
	
	@Test
	void registerUsertest3() throws Exception {
		String request = objectMapper.writeValueAsString(modelWrap);
		when(passwordEncoder.encode(anyString())).thenReturn("password");
		when(homeService.getTeamname(team.getName())).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(userService).saveUser(any(User.class));
		MvcResult result = mockMvc.perform(post("/userRegister")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
				.andExpect(status().is3xxRedirection())
				.andReturn();
		assertEquals(302, result.getResponse().getStatus());
		
	}

}
