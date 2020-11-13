package com.soprabanking.ips.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamServiceTest {

	@Mock
	private TeamDAO teamDAO;
	
	@InjectMocks
	private TeamService teamService;
	
	@Test
	public void testFetchAllTeams() throws Exception {
		
		Team t1 = new Team();
		Team t2 = new Team();
		
		List<Team> teams = new ArrayList<>();
		teams.add(t1);
		teams.add(t2);
		
		when(teamDAO.fetchAllTeams()).thenReturn(teams);
		
		List<Team> result = teamService.fetchAllTeams();
		
		assertNotNull(result);
		assertEquals(2, result.size());
	}
	
	@Test
	public void testFetchAllTeamsError() throws Exception {
		
		when(teamDAO.fetchAllTeams()).thenThrow(new RuntimeException());
		
		assertThrows(Exception.class, ()-> teamService.fetchAllTeams());
	}
}
