package com.soprabanking.ips.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
class UserDAOTest {
	
	private User user;
	private Team team;

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserDAO userDao;
	
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
	void getByIdtest() {
		
		when(userRepository.getOne(anyLong())).thenReturn(user);
		assertEquals(user.getEmail(), userDao.getById(1L).getEmail());
	}
	
	@Test
	void getUsertest() {
		
		when(userRepository.getUserByUserName("nk@gmail.com")).thenReturn(user);
		assertEquals(user.getEmail(), userDao.getUser("nk@gmail.com").getEmail());
	}
	
	@Test
	void updatePasswordtest() {
		
		when(userRepository.updatePassword("nk@gmail.com", "password")).thenReturn(0);
		assertThrows(IllegalArgumentException.class, ()->userDao.updatePassword("nk@gmail.com", "password"));
	}
	
	@Test
	void updatePasswordtest2() {
		
		when(userRepository.updatePassword("nk@gmail.com", "password")).thenReturn(1);
		assertDoesNotThrow(()->userDao.updatePassword("nk@gmail.com", "password"));
	}

}
