package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.soprabanking.ips.config.UserDetailsServiceImpl;
import com.soprabanking.ips.dao.UserRepository;
import com.soprabanking.ips.entities.User;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
	private User user1, user2;
	
	@Mock
	private UserRepository user_repo;
	
	@InjectMocks
	private UserDetailsServiceImpl  user_details_service;
	
	
	@BeforeEach
	public void init() {
		
		user1 = new User();
		user1.setId(1L);
		user1.setName("nishu");
		user1.setEmail("nk@gmail.com");
		user1.setPassword("password");
		
		user2 = new User();
		user2.setId(2L);
		user2.setName("raghav");
		user2.setEmail("rk@gmail.com");
		user2.setPassword("pass");
		
	}
	
	@Test()
	void SuccessfulLoginTest() {
		
		when(user_repo.getUserByUserName("nk@gmail.com")).thenReturn(user1);
		
		assertEquals(user1.getEmail(),user_details_service.loadUserByUsername("nk@gmail.com").getUsername());
	}
	
	@Test()
	void InvalidLoginTest() throws UsernameNotFoundException {
		
		when(user_repo.getUserByUserName("nk22@gmail.com")).thenThrow(UsernameNotFoundException.class);
		
		assertThrows(UsernameNotFoundException.class, () -> user_details_service.loadUserByUsername("nk22@gmail.com"));
	}

}