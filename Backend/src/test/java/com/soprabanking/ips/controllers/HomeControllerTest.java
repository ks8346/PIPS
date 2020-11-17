package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.modelwrap.ModelWrap;
import com.soprabanking.ips.services.HomeService;
import com.soprabanking.ips.services.UserControllerService;


@ExtendWith(MockitoExtension.class)
class HomeControllerTest {
	
	private ModelWrap modelWrap;
	private User user;
	private Team team;

	@Mock
	private UserControllerService userControllerService;
	
	@Mock
	private HomeService homeService;
	
	@Mock
	private BCryptPasswordEncoder encoder;
	
	@InjectMocks
	private HomeController homeController;
	
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
	void hometest() {
		
		Model model = new ConcurrentModel();
		
		assertEquals("this is home page of IPS", homeController.home(model));
	}
	
	@Test
	void findAllTeamstest1() {
		
		List<Object> teams = new ArrayList<>();
		Team team1 = new Team();
		team1.setId(1L);
		team1.setName("sparks");
		
		Team team2 = new Team();
		team2.setId(1L);
		team2.setName("sparks");
		
		teams.add(team1);
		teams.add(team2);
		
		when(homeService.GetTeam()).thenReturn(teams);
		
		assertEquals(teams.get(0).toString(), homeController.findAllTeams().getBody().get(0).toString());
	}
	
	@Test
	void findAllTeamstest2() {
		
		when(homeService.GetTeam()).thenThrow(IllegalStateException.class);
		
		assertTrue(homeController.findAllTeams().getBody().isEmpty());
	}
	
	@Test
	void registerUsertest1() {
		
		when(encoder.encode(anyString())).thenReturn("password");
		when(homeService.GetTeamname(team.getName())).thenReturn(team);
		Mockito.doNothing().when(userControllerService).saveUser(any(User.class));
		
		assertNull(homeController.registerUser(modelWrap).getBody());
		
	}
	
	@Test
	void registerUsertest2() {
		
		when(encoder.encode(anyString())).thenReturn("password");
		when(homeService.GetTeamname(team.getName())).thenReturn(null);
		Mockito.doNothing().when(userControllerService).saveUser(any(User.class));
		
		assertNull(homeController.registerUser(modelWrap).getBody());
		
	}
	
	@Test
	void registerUsertest3() {
		
		when(encoder.encode(anyString())).thenReturn("password");
		when(homeService.GetTeamname(team.getName())).thenReturn(null);
		Mockito.doThrow(new IllegalArgumentException()).when(userControllerService).saveUser(any(User.class));
		
		assertNull(homeController.registerUser(modelWrap).getBody());
		
	}
	
	@Test
	void basicAuthtest1() throws JsonProcessingException {
		
		Principal principal = new Principal() {
			public String getName() {
				return "nk@gmail.com";
			}
		};
		ObjectMapper mapper = new ObjectMapper();
		when(userControllerService.GetUserDetails("nk@gmail.com")).thenReturn(user);
		
		assertEquals(mapper.writeValueAsString(user), homeController.basicauth(principal).getBody().getMessage());
		
	}
	
	@Test
	void basicAuthtest2() {
		
		Principal principal = new Principal() {
			public String getName() {
				return "nk@gmail.com";
			}
		};
		when(userControllerService.GetUserDetails("nk@gmail.com")).thenThrow(EntityNotFoundException.class);
		
		assertNull(homeController.basicauth(principal).getBody());
		
	}

}
