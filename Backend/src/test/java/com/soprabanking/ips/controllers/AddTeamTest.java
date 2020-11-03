package com.soprabanking.ips.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.soprabanking.ips.dao.TeamRepository;
import com.soprabanking.ips.dao.UserRepository;
import com.soprabanking.ips.entities.Team;
import com.soprabanking.ips.entities.User;


class AddTeamTest {
	
	@Mock
	TeamRepository teamRepository;
	@Mock
	UserRepository userRepository;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testAddTeam() {
		Team team=new Team();
		team.setTeamname("alpha");
		team.setTid(1L);
		when(teamRepository.getTeamByTeamName(toString())).thenReturn(team);
		assertNotNull(team);
		assertEquals("alpha",team.getTeamname());
		assertEquals("sparks",team.getTeamname());
		assertEquals("devs",team.getTeamname());
		
	}
	@Test
	void testAddTeam_TeamNameNotFoundException()
	{
		//Team team=new Team();
		when(teamRepository.getTeamByTeamName(toString())).thenThrow(new RuntimeException("team not added"));
		teamRepository.getTeamByTeamName("kavya");
		
	}
	
	
		
		
		
		
	

}
