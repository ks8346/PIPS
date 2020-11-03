package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.soprabanking.ips.dao.TeamRepository;
import com.soprabanking.ips.dao.UserRepository;
import com.soprabanking.ips.entities.Team;
import com.soprabanking.ips.entities.User;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddUserTest {
	@Mock
	TeamRepository teamRepository;
	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testRegister() {
		User user=new User();
		Team team=new Team();
		user.setName("kavya");
		user.setEmail("kavya@gmail.com");
		user.setPassword("123");
		user.setTeam(team);
		when(teamRepository.getTeamByTeamName(toString())).thenReturn(team);
		when(userRepository.getUserByUserName(toString())).thenReturn(user);
		assertNotNull(user);
		assertEquals("kavya",user.getName());
		assertEquals("mukul",user.getName());
		assertEquals("priyanka",user.getName());
		
		

	}
	@Test
	void testAddUser_UserNameNotFoundException()
	{
		//Team team=new Team();
	
		when(userRepository.getUserByUserName(toString())).thenThrow(new RuntimeException("user not added"));
		userRepository.getUserByUserName("kavya");
		
	}

}
