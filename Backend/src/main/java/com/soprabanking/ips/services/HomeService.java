package com.soprabanking.ips.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;


/** 
 * Home Service
 * This is service class
 * this class is used to fetched the data of team from team database
 * @author kavsharma
 * 
 */
@Service
public class HomeService {

private static final Logger LOGGER = LogManager.getLogger(HomeService.class);
	
	/** 
	 * This method return list of the team which are in database
	 * @return list<team> , list of teamname
	 * 
	 */
	
    @Autowired
	private TeamRepository teamRepository;
	// fetching All Team name  from database
	public List<Object> GetTeam() {
		LOGGER.info("Inside HomeService : GetTeam() method");

		return teamRepository.getTeamIdANDName();

		
	}
	/** 
	 * This method fetch Team object by team_name  from database to check team exist in database or not
	 * @param teamname of string type that contains the name of the team 
	 * @return team1 , object of team
	 */
	
	public Team GetTeamname(String teamname)
	{
		LOGGER.info("Inside HomeService : GetTeamname() method");
	    Team team1 = this.teamRepository.getTeamByTeamName(teamname);
		return team1;
		
	}
	

}



