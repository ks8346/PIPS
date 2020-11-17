package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private User user;
	private Team team;
	
	@Mock
	private UserDAO userDao;
	
	@Mock
	private PasswordEncoder encoder;
	
	@InjectMocks
	private UserService userService;
	
	@BeforeEach
	void setUp() throws Exception {
		
		team = new Team();
		team.setId(1L);
		team.setName("sparks");
		
		user = new User();
		user.setEmail("nk@gmail.com");
		user.setId(1L);
		user.setName("nishu");
		user.setPassword("password");
		user.setTeam(team);
	}

	@Test
	void updatePasswordSuccessfulltest() {
		
		String password = encoder.encode("password");
		Mockito.doNothing().when(userDao).updatePassword("nk@gmail.com", password);
		
		assertDoesNotThrow(()->userService.updatePassword("nk@gmail.com", "password"));
		
	}
	
	@Test
	void updatePasswordUnsuccessfulltest() {
		
		String password = encoder.encode("password");
		Mockito.doThrow(new IllegalArgumentException()).when(userDao).updatePassword("nk@gmail.com", password);
		
		assertThrows(Exception.class, ()->userService.updatePassword("nk@gmail.com", "password"));
		
	}
	
	@Test
	void getUserSuccessfulltest() {
		
		when(userDao.getUser("nk@gmail.com")).thenReturn(user);
		
		assertEquals(user.getEmail(), userService.getUserByUsername("nk@gmail.com").getEmail());
		
	}
	
	@Test
	void getUserUnsuccessfulltest() {
		
		when(userDao.getUser("nk@gmail.com")).thenThrow(IllegalArgumentException.class);
		
		assertThrows(Exception.class, ()->userService.getUserByUsername("nk@gmail.com"));
		
	}

}
