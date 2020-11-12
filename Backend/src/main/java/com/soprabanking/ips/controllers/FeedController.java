package com.soprabanking.ips.controllers;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.services.FeedService;
import com.soprabanking.ips.services.ProposalService;

@RestController
@CrossOrigin
@RequestMapping("/feed")
public class FeedController {
	
	private static Logger LOGGER = LogManager.getLogger(FeedController.class);

    @Autowired
    private FeedService feedService;

    @Autowired
    ProposalService proposalService;



  
    @PostMapping("/all")
    public ResponseEntity<List<Proposal>> getAllProposalFeed(@RequestBody String body) {

        try {
        	LOGGER.info("Inside FeedController : getAllProposalFeed() method");
        	List<Proposal> list = feedService.fetchAllProposals(body);
        	if(list.isEmpty())
        		throw new Exception();
        	
        	LOGGER.info("Inside FeedController : getAllProposalFeed() SUCCESSFULL");
            return new ResponseEntity<List<Proposal>>(list,
                    HttpStatus.OK);
        	
        } catch (Exception e) {
        	LOGGER.info("Inside FeedController : getAllProposalFeed() FAILED");
            return new ResponseEntity<List<Proposal>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);

        }
        
    }

    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Proposal>> getUserProposalFeed(
            @RequestBody String body) {

        try {
        	List<Proposal> list = feedService.fetchUserProposals(body);
        	System.out.println(list);
        	if(list.isEmpty())
        		throw new Exception();
            return new ResponseEntity<>(list,
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
