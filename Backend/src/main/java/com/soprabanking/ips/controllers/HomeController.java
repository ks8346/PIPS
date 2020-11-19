package com.soprabanking.ips.controllers;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.authentication.AuthenticationBean;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.modelwrap.ModelWrap;
import com.soprabanking.ips.services.HomeService;
import com.soprabanking.ips.services.UserService;


/**
 * Home Controller
 * Provides Rest-APIs for user registration and login.
 *
 * <p>
 * This is a Home Controller Class which implements registration handler(registration rest-API), handler for fetching all teams 
 * from database(getTeam rest-API) and login handler (login rest-API)
 * @author kavsharma
 * 
 */
@CrossOrigin
@RestController
public class HomeController {
	
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);


    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private HomeService homeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    /**
	 * This method returns home page for our product.
	 * @param model object of Model class
	 * @return a string
	 * */	
    @RequestMapping("/home")
    @ResponseBody
    public String home(Model model) {
        
        return "this is home page of IPS";
    }
    /**
	 * This method returns list of the team which are already exist in our database.
	 *
	 * @return a list of team names.
	 * */

    @GetMapping("/getTeam")
    public ResponseEntity<List<Object>> findAllTeams() {
    	 try {
    	LOGGER.info("Inside HomeController : finaALlTeam() method");
    	List<Object> allteam = homeService.getTeam();
        LOGGER.info("Inside HomeController : finaAllTeam() SUCCESS");
            return new ResponseEntity<>(allteam, HttpStatus.OK);
        } catch (Exception e) {
        LOGGER.error("Inside HomeController : findAllTeam() FAILURE", e);
            return new ResponseEntity<List<Object>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }
    /**
   	 * This method returns the response message which shows whether the user has successfully registered or not.
   	 * @param modelWrap the modelwrap has both user object and team object.
   	 * @return ResponseEntity with HTTP Status .
   	 * 
   	 * */

    @PostMapping("/userRegister")
    public ResponseEntity<String> registerUser(@RequestBody ModelWrap modelWrap) {
    	try {
        	LOGGER.info("Inside HomeController : registerUser() method");
            User user = modelWrap.getUser();
            Team team = modelWrap.getTeam();
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String tname=team.getName();
            Team team1 = homeService.getTeamname(tname);
            if (team1 == null) {
                user.setTeam(team);
            } else {
                user.setTeam(team1);
            }
            userService.saveUser(user);
            LOGGER.info("Inside HomeController : registerUser() SUCCESS");
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
        	LOGGER.error("Inside HomeController :registerUser() FAILURE", e);
            
            return new ResponseEntity<String>(HttpStatus.FOUND);
        }


    }
    /**
   	 * This method returns all details of user when the user logins into the system and shows that details on the landing page.
   	 * @param principal the principal has the object of that user who logins into the system.
   	 * @return ResponseEntity with HTTP Status .
   	 * */
    

    @GetMapping(path = "/signIn") // /signIn
    public ResponseEntity<AuthenticationBean> basicauth(Principal principal) {
        try {
        	LOGGER.info("Inside HomeController : basicauth() method");
            String userName = principal.getName();
            User user = userService.getUserDetails(userName);
            String s = objectMapper.writeValueAsString(user);
            LOGGER.info("Inside HomeController : basicauth() SUCCESS");
            return new ResponseEntity<AuthenticationBean>(new AuthenticationBean(s), HttpStatus.OK);

        } catch (Exception e) {
        	LOGGER.error("Inside HomeController : basicauth() FAILURE", e);
            return new ResponseEntity<AuthenticationBean>(HttpStatus.UNAUTHORIZED);
        }
    }


}


	

