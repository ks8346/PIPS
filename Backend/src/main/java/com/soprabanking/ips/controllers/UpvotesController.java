package com.soprabanking.ips.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.soprabanking.ips.models.Upvotes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soprabanking.ips.services.UpvotesService;

import java.util.List;
/**
 * 
 * 
 *Upvotes Controller
 * 
 * 		This is a controller class for upvotes done on proposals.
 * 		The UpvotesController Class implements an application which does the following
 * 		<br>
 * 		<ul>
 * 			<li>like <br></li>
 * 			<li>dislike <br></li>
 * 			<li>return if a user has upvoted a proposal <br></li>
 * 		</ul>
 * 
 * @author mojha
 * <br>
 * 	@see org.springframework.web.bind.annotation.RestController
 * 	@see org.springframework.web.bind.annotation.CrossOrigin
 * 	@see org.springframework.web.bind.annotation.RequestMapping
 * 	@see org.springframework.beans.factory.annotation.Autowired
 * 	@see org.springframework.web.bind.annotation.PostMapping
 */

@RestController
@CrossOrigin
@RequestMapping("/upvotes")
public class UpvotesController {
    @Autowired
    private UpvotesService upvotesService;

    private static final Logger LOGGER = LogManager.getLogger(UpvotesController.class);
	
    /*GetMapping("/upvotes")
	public void displayUpvotes(@RequestParam Long proposalId)
	{
		System.out.println(upvotesService.listUpvotes(proposalId));
    	//return new ResponseEntity<List<Upvotes>>(upvotesService.listUpvotes(proposalId),HttpStatus.OK);
	}*/
    /**
     * This is a method used to Like a proposal in a String which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>upvotesService : an Object of the UpvotesService<br></li>
     * 			<li>upvoteProposal : a method in class UpvotesService called by object upvotesService<br></li>
     * </ul>
     * @param body of string type containing the upvote
     * @return Response Entity with a success string with a OK Http Status
     * @see com.soprabanking.ips.services.UpvotesService
     * @see com.soprabanking.ips.models.Upvotes
     * 
     */
    @PostMapping(value = "/like", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upvoteProposal(@RequestBody String body) {
        try {
            LOGGER.info("Inside UpvotesController: upvoteProposal() method");
            upvotesService.upvoteProposal(body);
            LOGGER.info("Inside UpvotesController: upvoteProposal() SUCCESS");
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Inside UpvotesController: upvoteProposal() FAILURE");
            return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
        }
    }
    /**
     * This is a method used to dislike a proposal in a String which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>upvotesService : an Object of the UpvotesService<br></li>
     * 			<li>reverseUpvote : a method in class UpvotesService called by object upvotesService<br></li>
     * </ul>
     * @param body of string type containing the dislike
     * @return Response Entity with a success string with a OK Http Status
     * @see com.soprabanking.ips.services.UpvotesService
     * @see com.soprabanking.ips.models.Upvotes
     * 
     */

    @PostMapping(value = "/dislike", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reverseupvoteProposal(@RequestBody String body) {

        try {
            LOGGER.info("Inside UpvotesController: reverseupvoteProposal() method");
            upvotesService.reverseUpvote(body);
            LOGGER.info("Inside UpvotesController: reverseupvoteProposal() SUCCESS");
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);

        } catch (Exception e) {
            LOGGER.error("Inside UpvotesController: reverseupvoteProposal() FAILURE");
            return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
        }


    }
    /**
     * This is a method used to check if a proposal has been upvoted by the user in a String which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>upvotesService : an Object of the UpvotesService<br></li>
     * 			<li>hasUpvoted : a method in class UpvotesService called by object upvotesService<br></li>
     * </ul>
     * @param body of string type containing the dislike
     * @return Response Entity with a boolean true value if the user has upvoted the proposal with a OK Http Status
     * @see com.soprabanking.ips.services.UpvotesService
     * @see com.soprabanking.ips.models.Upvotes
     * 
     */

    @PostMapping(value = "/hasUpvoted", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasUpvotedOrNot(@RequestBody String body) {
        try {
            LOGGER.info("Inside UpvotesController: hasUpvotedOrNot() method");
            boolean upvoted=upvotesService.hasUpvoted(body);
            LOGGER.info("Inside UpvotesController: hasUpvotedOrNot() SUCCESS");
            return new ResponseEntity<Boolean>(upvoted, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Inside UpvotesController: hasUpvotedOrNot() FAILURE");
            return new ResponseEntity<Boolean>(false, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
