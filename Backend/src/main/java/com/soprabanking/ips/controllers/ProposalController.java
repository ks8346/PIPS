package com.soprabanking.ips.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
			return new ResponseEntity<Proposal>(proposalService.saveProposal(body),HttpStatus.OK);
		}
		catch(Exception ex) {
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
			return new ResponseEntity<Proposal>(proposalService.updateProposal(body),HttpStatus.OK);
		}
		catch(Exception ex) {
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
            proposalService.shareProposal(body);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
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
		return proposalService.deleteProposal(body)?new ResponseEntity<String>("SUCCESS", HttpStatus.OK):new ResponseEntity<String>("FAILURE", HttpStatus.NOT_ACCEPTABLE);
	}

}
