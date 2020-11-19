package com.soprabanking.ips.daos;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.services.TeamService;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * This class is a Data Access Object Class for <a href="Team.html">{@link com.soprabanking.ips.models.Team}</a> in the system.
 * It has the following methods
 * <ol>
 * <li>getTeam</li>
 * <li>fetchAllTeams</li>
 * </ol>
 * 
 * @author mojha
 * @see org.springframework.stereotype.Component
 * @see org.springframework.beans.factory.annotation.Autowired
 */
@Component
public class TeamDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(TeamDAO.class);

    @Autowired
    TeamRepository teamRepository;

    /**
     * This method is used to fetch all the team information by the team id
     * @param id: id of long type
     * @return A team which Matches the id passed
     */
    public Team getTeam(long id) {
    	LOGGER.info("Inside TeamDAO : getTeam() method");
        return teamRepository.findById(id);
    }

    /**
     * This method is used fetch  the list of all the teams present in the Database
     * @return A list of all teams
     */
    public List<Team> fetchAllTeams() {
    	LOGGER.info("Inside TeamDAO : fetchAllTeams() method");
        return teamRepository.findAll();
    }
    /**
     * This method is used to get team id and name.<br>
     * Variables used
     * <br>1)teamRepository : an Object of the <a href="TeamRepository.html">{@link com.soprabanking.ips.repositories.TeamRepository}</a>
     * @return Team
     */
    public List<Object> getTeamdao() {
        LOGGER.info("Inside HomeService : getTeamdao() method");

        return teamRepository.getTeamIdANDName();

    }
    /**
     * This method is used to get team name.<br>
     * Variables used
     * <br>1)teamRepository : an Object of the <a href="TeamRepository.html">{@link com.soprabanking.ips.repositories.TeamRepository}</a>
     *@param teamname: team name of string type 
     * @return Team
     */
    
    public Team getTeamnamedao(String teamname)
    {
        LOGGER.info("Inside HomeService : getTeamnamedao() method");
        Team team1 = this.teamRepository.getTeamByTeamName(teamname);
        return team1;
        
    }
}

