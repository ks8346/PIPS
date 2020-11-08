package com.soprabanking.ips.controllers;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.ProposalRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.services.FeedService;
import com.soprabanking.ips.services.ProposalService;

@RestController
@CrossOrigin
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @Autowired
    ProposalService proposalService;
     
    @PostMapping("/all")
    public ResponseEntity<List<Proposal>> getAllProposalFeed(@RequestBody String body) {

        try {
            return new ResponseEntity<>(feedService.fetchAllProposals(body),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Proposal>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);

        }
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proposal>> getUserProposalFeed(
            @RequestBody String body) {

        try {
            return new ResponseEntity<>(feedService.fetchUserProposals(body),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<List<Proposal>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/team")
    public ResponseEntity<Object> getTeamProposalFeed(@RequestBody String body) {
        Optional<List<Proposal>> optionalProposalDtoList = Optional.ofNullable(proposalService.getDefault(body));
        return optionalProposalDtoList.<ResponseEntity<Object>>map(proposals -> new ResponseEntity<>(proposals, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE));
    }


}
