package com.soprabanking.ips.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;

@Service
public class TeamService {

	@Autowired
	private TeamDAO teamDAO;
	
	public List<Team> fetchAllTeams() throws Exception {
		
		try {
			return teamDAO.fetchAllTeams();
		}
		catch(Exception ex){
			throw new Exception();
		}
	}
	
}
