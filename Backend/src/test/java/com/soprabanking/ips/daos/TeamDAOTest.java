package com.soprabanking.ips.daos;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeamDAOTest {

	@Mock
	private TeamRepository teamRepository;
	
	@InjectMocks
	private TeamDAO teamDAO;
	
	@Test
	public void testGetTeam() {
		
		Team t = new Team();
		t.setName("Devs");
		
		when(teamRepository.findById(1L)).thenReturn(t);
		
		assertEquals("Devs", teamDAO.getTeam(1L).getName());
	}
	
	@Test
	public void testFetchAllTeams() {
		
		Team t1 = new Team();
		Team t2 = new Team();
		
		List<Team> teams = new ArrayList<>();
		teams.add(t1);
		teams.add(t2);
		
		when(teamRepository.findAll()).thenReturn(teams);
		
		List<Team> result = teamDAO.fetchAllTeams();
		assertNotNull(result);
		assertEquals(2, result.size());
	}
}
