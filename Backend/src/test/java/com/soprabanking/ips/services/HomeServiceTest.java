package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;

@SpringBootTest
class HomeServiceTest {
	
	@MockBean
	private TeamDAO teamDao;
	
	@Autowired
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
		
		when(teamDao.getTeamdao()).thenReturn(teams);
		assertEquals(teams.get(0).toString(), homeService.getTeam().get(0).toString());
		verify(teamDao).getTeamdao();
		verifyNoMoreInteractions(teamDao);
	}
	
	@Test
	void getTeam() {
		Team team = new Team();
		team.setId(1L);
		team.setName("sparks");
		when(teamDao.getTeamnamedao("sparks")).thenReturn(team);
		assertEquals(team.getName(), homeService.getTeamname("sparks").getName());
		verify(teamDao).getTeamnamedao(anyString());
		verifyNoMoreInteractions(teamDao);
	}

}
