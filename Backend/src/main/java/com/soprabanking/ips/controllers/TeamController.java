package com.soprabanking.ips.controllers;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<Team>> getAllTeams() {

        try {
            return new ResponseEntity<>(teamService.fetchAllTeams(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<List<Team>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}
