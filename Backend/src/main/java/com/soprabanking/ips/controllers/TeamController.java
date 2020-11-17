package com.soprabanking.ips.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.services.TeamService;
/**
 * Team Controller
 * 		 This is a controller class for all the teams that are in the system.It has the getAllTeams method.
 * @author mojha
 *	@see org.springframework.web.bind.annotation.RestController
 * 	@see org.springframework.web.bind.annotation.CrossOrigin
 * 	@see org.springframework.web.bind.annotation.RequestMapping
 * 	@see org.springframework.beans.factory.annotation.Autowired
 * 	@see org.springframework.web.bind.annotation.PostMapping
 */
@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    private static final Logger LOGGER = LogManager.getLogger(TeamController.class);
    /**
     * This method is used to get the information of all <a href="Team.html">{@link com.soprabanking.ips.models.Team}</a>.
     * <br>Variables used
     * <ul>
     * <li>teamService: an Object of the <a href="TeamService.html">{@link com.soprabanking.ips.services.TeamService}</a></li>
     * <li>teams : a list of team object</li>
     * <li>fetchAllTeams() :a method in class TeamService called by object teamService</li>
     * </ul>
     * @return A list of all teams with a Http Ok status
     * @see com.soprabanking.ips.models.Team
     * @see com.soprabanking.ips.services.TeamService
     */
    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {

        try {
            LOGGER.info("Inside TeamController: getAllTeams() method");
            List<Team> teams=teamService.fetchAllTeams();
            LOGGER.info("Inside TeamController: getAllTeams() SUCCESS");
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Inside TeamController: getAllTeams() FAILURE");
            return new ResponseEntity<List<Team>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
