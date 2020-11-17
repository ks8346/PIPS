package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;

@ExtendWith(MockitoExtension.class)
class HomeServiceTest {
	
	@Mock
	private TeamRepository teamRepository;
	
	@InjectMocks
	private HomeService homeService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void getTeamtest() {
		
		List<Object> teams = new ArrayList<>();
		Team team1 = new Team();
		team1.setId(1L);
		team1.setName("sparks");
		
		Team team2 = new Team();
		team2.setId(1L);
		team2.setName("sparks");
		
		teams.add(team1);
		teams.add(team2);
		
		when(teamRepository.getTeamIdANDName()).thenReturn(teams);
		
		assertEquals(teams.get(0).toString(), homeService.getTeam().get(0).toString());
	}
	
	@Test
	void getTeam() {
		
		Team team = new Team();
		team.setId(1L);
		team.setName("sparks");
		
		when(teamRepository.getTeamByTeamName("sparks")).thenReturn(team);
		
		assertEquals(team.getName(), homeService.getTeamname("sparks").getName());
	}

}
