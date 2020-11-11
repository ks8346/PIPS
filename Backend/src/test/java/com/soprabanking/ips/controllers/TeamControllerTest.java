package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.services.TeamService;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@MockBean
	private TeamService teamService;
	
	@Test
	public void testGetAllTeams() throws Exception {
		
		Team t1 = new Team();
		Team t2 = new Team();
		List<Team> teams = new ArrayList<>();
		teams.add(t1);
		teams.add(t2);
		
		when(teamService.fetchAllTeams()).thenReturn(teams);
		
		MvcResult mvcResult = mockMvc.perform(get("/team/all"))
					.andExpect(status().isOk())
					.andReturn();
		
		List<Team> result= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Team[].class));
        assertEquals(2, result.size());
	}
	
	@Test
	public void testGetAllTeamsEmpty() throws Exception {
		
		when(teamService.fetchAllTeams()).thenThrow(new RuntimeException());		
		MvcResult mvcResult = mockMvc.perform(get("/team/all"))
					.andExpect(status().is4xxClientError())
					.andReturn();
		
		List<Team> result= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Team[].class));
        assertTrue(result.isEmpty());
	}
}
