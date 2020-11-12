package com.soprabanking.ips.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;

@Service
public class TeamService {
	
	private static final Logger LOGGER = LogManager.getLogger(TeamService.class);

    @Autowired
    private TeamDAO teamDAO;

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
