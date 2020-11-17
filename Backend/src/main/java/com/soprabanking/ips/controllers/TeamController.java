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

@RestController
@CrossOrigin
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    private static final Logger LOGGER = LogManager.getLogger(TeamController.class);

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
