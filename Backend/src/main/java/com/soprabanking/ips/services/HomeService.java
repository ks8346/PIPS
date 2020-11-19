package com.soprabanking.ips.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;



/** 
 * Home Service
 * This is service class
 * this class is used to fetched the data of team from team database
 * @author kavsharma
 * @see com.soprabanking.ips.models.User
 * @see com.soprabanking.ips.models.Team
 * @see com.soprabanking.ips.daos.UserDAO
 * @see com.soprabanking.ips.daos.TeamDAO
 * @see com.soprabanking.ips.controllers.HomeController 
 * 
 */
@Service
public class HomeService {

	private static final Logger LOGGER = LogManager.getLogger(HomeService.class);

	/**
	 * {@link TeamDAO} object responsible for fetching teams and fetching the list of team names {@link Team} objects by interacting with the persistence layer 
	 */

	@Autowired
	private TeamDAO teamDao;
	/** 
	 * This method returns list of the team which are in database
	 * @return list of teamname
	 * 
	 */

	public List<Object> getTeam() {
		LOGGER.info("Inside HomeService : getTeam() method");

		return teamDao.getTeamdao();
	}
	/** 
	 * This method fetches Team object by teamname  from database to check if team exists in database or not
	 * @param teamname: teamname of string type that contains the name of the team 
	 * @return team1 , object of team
	 */

	public Team getTeamname(String teamname)
	{
		LOGGER.info("Inside HomeService : getTeamname() method");
		Team team1 =this.teamDao.getTeamnamedao(teamname);
		return team1;

	}
}


