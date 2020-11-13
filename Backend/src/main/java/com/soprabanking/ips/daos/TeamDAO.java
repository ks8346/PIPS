package com.soprabanking.ips.daos;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.services.TeamService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(TeamDAO.class);

    @Autowired
    TeamRepository teamRepository;

    public Team getTeam(long id) {
    	LOGGER.info("Inside TeamDAO : getTeam() method");
        return teamRepository.findById(id);
    }

    public List<Team> fetchAllTeams() {
    	LOGGER.info("Inside TeamDAO : fetchAllTeams() method");
        return teamRepository.findAll();
    }
}
