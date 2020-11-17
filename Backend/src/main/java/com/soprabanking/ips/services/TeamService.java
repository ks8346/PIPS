package com.soprabanking.ips.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;
/** 
 * Team Service
 * This is a service class
 * this class is used to fetch all the team names
 * @author kavsharma
 * 
 * 
 */
@Service
public class TeamService {
	
	private static final Logger LOGGER = LogManager.getLogger(TeamService.class);
	/**
	* {@link TeamDAO} object responsible for fetching teams and fetching the list of team names {@link Team} objects by interacting with the persistence layer 
	*/
		
    @Autowired
    private TeamDAO teamDAO;
    /** 
     * This method returns list of the team which are in database
     * @return list of team names
     * @throws Exception when the list of team is null
     * 
     */

    public List<Team> fetchAllTeams() throws Exception {

        try {
        	LOGGER.info("Inside TeamService : fetchAllTeams() method");
            List<Team> teams = teamDAO.fetchAllTeams();

        	LOGGER.info("Inside TeamService : fetchAllTeams() SUCCESS");
        	return teams;
        } catch (Exception ex) {
        	LOGGER.error("Inside TeamService : fetchAllTeams() FAILURE", ex);
            throw new Exception();
        }
    }

}
