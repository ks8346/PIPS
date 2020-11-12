package com.soprabanking.ips.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;

@Service
public class HomeService {
	

	
    @Autowired
	private TeamRepository teamRepository;
	// fetching All Team name  from database
	public List<Object> GetTeam() {

		return teamRepository.getTeamIdANDName();

		
	}
	// fetch Team object by team_name  from database to check team exist in database or not
	
	public Team GetTeamname(String teamname)
	{
		
	    Team team1 = this.teamRepository.getTeamByTeamName(teamname);
		return team1;
		
	}
	
	

}



