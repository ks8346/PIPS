package com.soprabanking.ips.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
import com.soprabanking.ips.services.ProposalService;
/**
 * Proposal Controller
 * This is a controller class for proposals which will be seen on different feeds.
 * 		The ProposalController Class implements an application which does the following
 * 		<br>
 * 		<ul>
 * 			<li>add Proposal <br></li>
 * 			<li>delete Proposal <br></li>
 * 			<li>update Proposal <br></li>
 * 			<li>share Proposal <br></li>
 * 		</ul>
 * @author mojha
 *<br>
 * 	@see org.springframework.web.bind.annotation.RestController
 * 	@see org.springframework.web.bind.annotation.CrossOrigin
 * 	@see org.springframework.web.bind.annotation.RequestMapping
 * 	@see org.springframework.beans.factory.annotation.Autowired
 * 	@see org.springframework.web.bind.annotation.PostMapping
 */
@RestController
@CrossOrigin
@RequestMapping("/proposal")
public class ProposalController {
	
	private static final Logger LOGGER = LogManager.getLogger(ProposalController.class);

	
	@Autowired
	private ProposalService proposalService;
	
	 /**
     * This is a method used to Add proposal in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status 
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>proposalService : an Object of the ProposalService<br></li>
     * 			<li> saveProposal : a method in class ProposalService called by object proposalSService<br></li>
     * </ul>
     * @param body of string type containing the proposal object
     * @return Response Entity with the saved proposal with a OK Http Status
     * @see com.soprabanking.ips.services.ProposalService
     * @see com.soprabanking.ips.models.Proposal
     */
	@PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Proposal> addProposal(@RequestBody String body){
		
		try {
			LOGGER.info("Inside ProposalController : addProposal() method");
			Proposal proposal = proposalService.saveProposal(body);
			LOGGER.info("Inside ProposalController : addProposal() SUCCESS");
			return new ResponseEntity<Proposal>(proposal, HttpStatus.OK);
		}
		catch(Exception ex) {
			LOGGER.error("Inside ProposalController : addProposal() FAILURE", ex);
			return new ResponseEntity<Proposal>(new Proposal(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	 /**
     * This is a method used to Update proposal in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status 
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>proposalService : an Object of the ProposalService<br></li>
     * 			<li> updateProposal : a method in class ProposalService called by object proposalSService<br></li>
     * </ul>
     * @param body of string type containing the proposal object
     * @return Response Entity with the updated proposal with a OK Http Status
     * @see com.soprabanking.ips.services.ProposalService
     * @see com.soprabanking.ips.models.Proposal
     * 
     */
	@PostMapping(value = "/update", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<Proposal> updateProposal(@RequestBody String body){
		
		try {
			LOGGER.info("Inside ProposalController : updateProposal() method");
			Proposal proposal = proposalService.updateProposal(body);
			LOGGER.info("Inside ProposalController : updateProposal() SUCCESS");
			return new ResponseEntity<Proposal>(proposal, HttpStatus.OK);
		}
		catch(Exception ex) {
			LOGGER.error("Inside ProposalController : updateProposal() FAILURE", ex);
			return new ResponseEntity<Proposal>(new Proposal(), HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	 /**
     * This is a method used to Share proposal in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status 
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>proposalService : an Object of the ProposalService<br></li>
     * 			<li> shareProposal : a method in class ProposalService called by object proposalSService<br></li>
     * </ul>
     * @param body of string type containing the comment object
     * @return Response Entity with a Success message in String with a OK Http Status
     * @see com.soprabanking.ips.services.ProposalService
     * 
     * 
     */
	
	@PostMapping(value = "/share", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> shareProposal(@RequestBody String body) {
        try {
        	LOGGER.info("Inside ProposalController : shareProposal() method");
            proposalService.shareProposal(body);
            
            LOGGER.info("Inside ProposalController : shareProposal() SUCCESS");
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception ex) {
        	LOGGER.error("Inside ProposalController : shareProposal() FAILURE", ex);
            return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
        }
    }
	 /**
     * This is a method used to delete proposal in a string which is sent in a Response Entity.
     * ResponseEntity represents an HTTP response, including headers, body, and status 
     * <br>Some variables used  in this method are
     * <ul>
     * 			<li>proposalService : an Object of the ProposalService<br></li>
     * 			<li> deleteProposal : a method in class ProposalService called by object proposalSService<br></li>
     * </ul>
     * @param body of string type containing the comment object
     * @return Response Entity with a Success message in String with a OK Http Status
     * @see com.soprabanking.ips.services.ProposalService
     * 
     */

	@PostMapping("/delete")
	public ResponseEntity<String> deleteProposal(@RequestBody String body){
		
		try {
			LOGGER.info("Inside ProposalController : deleteProposal() method");
		
			if(proposalService.deleteProposal(body)) {
				LOGGER.info("Inside ProposalController : deleteProposal() SUCCESS");
				return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}
	
			LOGGER.error("Inside ProposalController : deleteProposal() FAILURE");
			return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
		}
		catch(Exception ex) {
			LOGGER.error("Inside ProposalController : deleteProposal() FAILURE", ex);
			return new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
