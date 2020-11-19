package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;

@SpringBootTest
class UserServiceTest {
	
	private User user;
	private Team team;
	
	@MockBean
	private UserDAO userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

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
		
		when(userDao.getUser("nk@gmail.com")).thenReturn(user);
		assertEquals(user.getEmail(), userService.getUserDetails("nk@gmail.com").getEmail());
		verify(userDao).getUser(anyString());
		verifyNoMoreInteractions(userDao);
	}
	
	@Test
	void saveUsertest() {
		
		Mockito.doNothing().when(userDao).saveUserdao(user);
		assertDoesNotThrow(()->userService.saveUser(user));
		verify(userDao).saveUserdao(any(User.class));
		verifyNoMoreInteractions(userDao);
	}
	
	@Test
	void updatePasswordtest() {
		String username = "nk@gmail.com";
		String password = passwordEncoder.encode("password");
		Mockito.doNothing().when(userDao).updatePassword(username, password);
		assertDoesNotThrow(()->userService.updatePassword(username, password));
		verify(userDao).updatePassword(anyString(), anyString());
		verifyNoMoreInteractions(userDao);
	}

}
