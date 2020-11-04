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

import com.soprabanking.ips.services.UpvotesService;

@RestController
@CrossOrigin
@RequestMapping("/upvotes")
public class UpvotesController
{
	@Autowired
	private UpvotesService upvotesService;
	
    /*@GetMapping("/upvotes")
	public ResponseEntity<List<Upvotes>> displayUpvotes(@RequestParam Long proposalId)
	{
    	return new ResponseEntity<List<Upvotes>>(upvotesService.listUpvotes(proposalId),HttpStatus.OK);
	}*/
    
    @PostMapping(value="/like",consumes =APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upvoteProposal(@RequestBody String body)
    {
    	try
    	{
    		upvotesService.upvoteProposal(body);
    	return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
        
    	}
    	catch(Exception e)
    	{
    		return new ResponseEntity<String>("FAILURE",HttpStatus.NOT_ACCEPTABLE);
    	}
    }
    
    @PostMapping(value="/dislike",consumes =APPLICATION_JSON_VALUE)
    public ResponseEntity<String> reverseupvoteProposal(@RequestBody String body)
    {
         
         try
     	{
        	 upvotesService.reverseUpvote(body);
     	return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
         
     	}
     	catch(Exception e)
     	{
     		return new ResponseEntity<String>("FAILURE",HttpStatus.NOT_ACCEPTABLE);
     	}
         
        
   	}
    @PostMapping(value="/hasUpvoted",consumes =APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasUpvotedOrNot(@RequestBody String body)
    {
       try
       {
    	  return new ResponseEntity<Boolean>(upvotesService.hasUpvoted(body),HttpStatus.OK);
       }
       catch(Exception e)
       {
    	   return new ResponseEntity<Boolean>(false,HttpStatus.NOT_ACCEPTABLE);
       }
    }
}
