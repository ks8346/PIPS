package com.soprabanking.ips.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;


/** 
 * This is service class
 * this class is used to fetched the data of team from team database
 * 
 */
@Service
public class HomeService {
	
	/** 
	 * This method return list of the team which are in database
	 * @return list<team> , list of teamname
	 * 
	 */
	
    @Autowired
	private TeamRepository teamRepository;
	// fetching All Team name  from database
	public List<Object> GetTeam() {

		return teamRepository.getTeamIdANDName();

		
	}
	/** 
	 * This method fetch Team object by team_name  from database to check team exist in database or not
	 * @param string  team name 
	 * @return team , object of team
	 */
	
	public Team GetTeamname(String teamname)
	{
		
	    Team team1 = this.teamRepository.getTeamByTeamName(teamname);
		return team1;
		
	}
	
	

}



