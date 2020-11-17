package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserControllerServiceTest {
	
	private User user;
	private Team team;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserControllerService userControllerService;

	@BeforeEach
	void init() {
		
		team = new Team();
		team.setId(1L);
		team.setName("sparks");
		
		user = new User();
		user.setId(1L);
		user.setEmail("nk@gmail.com");
		user.setName("nishu");
		user.setPassword("password");
		user.setTeam(team);
	}
	
	@Test
	void getUsertest() {
		
		when(userRepository.getUserByUserName("nk@gmail.com")).thenReturn(user);
		
		assertEquals(user.getEmail(), userControllerService.getUserDetails("nk@gmail.com").getEmail());
	}
	
	@Test
	void saveUsertest() {
		
		when(userRepository.save(user)).thenReturn(user);
		assertDoesNotThrow(()->userControllerService.saveUser(user));
		
	}

}
