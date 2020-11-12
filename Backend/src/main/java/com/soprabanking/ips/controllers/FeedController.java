package com.soprabanking.ips.controllers;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
/**
 * 
 * 
 * Feed Controller
 * 
 * 		This is a controller class to show the different Feed streams on the Landing page.
 * 		The FeedController Class implements an application which does the following
 * 		<br>
 * 		<ul>
 * 			<li>All Proposal Feed <br></li>
 * 			<li>User Proposal Feed <br></li>
 * 			<li>Team Proposal Feed<br></li>
 * 		</ul>
 * 
 * 
 * @author mojha
 * 	@see org.springframework.web.bind.annotation.RestController
 * 	@see org.springframework.web.bind.annotation.CrossOrigin
 * 	@see org.springframework.web.bind.annotation.RequestMapping
 * 	@see org.springframework.beans.factory.annotation.Autowired
 * 	@see org.springframework.web.bind.annotation.PostMapping
 * 
 */
@RestController
@CrossOrigin
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @Autowired
    ProposalService proposalService;
    /**
    * This is a method used to Display all proposals made by all users in a List which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * Some variables used  in this method are
     * <ul>
     * 			<li>list :List of objects of Proposal Type<br></li>
     * 			<li>feedService : an Object of the FeedService class<br></li>
     * 			<li>fetchAllProposals : a method in class FeedService called by object feedService<br></li>
     * </ul>
     * Exception if no proposals return an empty array list with Http Status Not Acceptable
     * @param body of string type containing the proposal object
     * @return Response Entity with a list of proposals from list with a OK Http Status
     * @see com.soprabanking.ips.services.FeedService
     * @see com.soprabanking.ips.models.Proposal
     */


  
    @PostMapping("/all")
    public ResponseEntity<List<Proposal>> getAllProposalFeed(@RequestBody String body) {

        try {
        	List<Proposal> list = feedService.fetchAllProposals(body);
        	if(list.isEmpty())
        		throw new Exception();
            return new ResponseEntity<List<Proposal>>(list,
                    HttpStatus.OK);
        	
        } catch (Exception e) {
            return new ResponseEntity<List<Proposal>>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE);

        }
        
    }

    /**
     * This is a method used to Display user proposals made by the user who is logged in, in a List which is sent in a Response Entity.
      * ResponseEntity represents an HTTP response, including headers, body, and status
      *  Some variables used  in this method are
      * <ul>
      * 			<li>list :List of objects of Proposal Type<br></li>
      * 			<li>feedService : an Object of the FeedService class<br></li>
      * 			<li>fetchUserProposals : a method in class FeedService called by object feedService<br></li>
      * </ul>
      * Exception if no proposals return an empty array list with Http Status Not Acceptable
      * @param body of string type containing the proposal object
      * @return Response Entity with a list of proposals from list with a OK Http Status
      * @see com.soprabanking.ips.services.FeedService
      * @see com.soprabanking.ips.models.Proposal
      */

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
    
    /**
     * This is a method used to Display team proposals marked to the team of the user who is logged in, in a List which is sent in a Response Entity.
      * This is also the default view of the User. If no proposal is marked to the team this feed will be empty.
      *  Some variables used  in this method are
      * <ul>
      * 			<li>list :List of objects of Proposal Type<br></li>
      * 			<li>feedService : an Object of the FeedService class<br></li>
      * 			<li>getDefault : a method in class FeedService called by object feedService<br></li>
      * </ul>
      * Exception if no proposals return an empty array list with Http Status Not Acceptable
      * @param body of string type containing the proposal objects
      * @return Response Entity with a list of proposals from list with a OK Http Status
      * @see com.soprabanking.ips.services.FeedService
      * @see com.soprabanking.ips.models.Proposal
      */


    @PostMapping("/team")
    public ResponseEntity<Object> getTeamProposalFeed(@RequestBody String body) {
        Optional<List<Proposal>> optionalProposalDtoList = Optional.ofNullable(proposalService.getDefault(body));
        return optionalProposalDtoList.<ResponseEntity<Object>>map(proposals -> new ResponseEntity<>(proposals, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_ACCEPTABLE));
    }


}
