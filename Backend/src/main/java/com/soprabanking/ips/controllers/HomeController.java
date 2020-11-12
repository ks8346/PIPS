package com.soprabanking.ips.controllers;

import java.security.Principal;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.authentication.AuthenticationBean;
import com.soprabanking.ips.helper.Message;
import com.soprabanking.ips.modelwrap.ModelWrap;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.services.HomeService;
import com.soprabanking.ips.services.UserControllerService;

@CrossOrigin
@RestController
/**
 * Provides Rest-APIs for user registration and login.
 *
 * <p>
 * This is a Home Controller Class which implements registration handler(registration rest-API), handler for fetching all teams 
 * from database(getTeam rest-API) and login handler (login rest-API)
 * 
 */

public class HomeController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private HomeService homeService;
    
    @Autowired
    private UserControllerService userControllerService;
    /**
	 * This method returns home page for our product.
	 *
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

        //List<Object> allteam = teamRepository.getTeamIdANDName();
    	List<Object> allteam = homeService.GetTeam();
        allteam.forEach(e -> {
            System.out.println(e);
        });
        try {
            return new ResponseEntity<>(homeService.GetTeam(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Object>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
        }
    }
    /**
   	 * This method returns the response message which shows whether the user has successfully registered or not.
   	 * @param modelwrap the modelwrap has both user object and team object.
   	 * @return ResponseEntity with HTTP Status .
   	 * @exception e this exception occurs when user enters email which already exists in our database.
   	 * */

    @PostMapping("/userRegister")
    public ResponseEntity<String> registerUser(@RequestBody ModelWrap modelWrap) {


        try {
            User user = modelWrap.getUser();
            Team team = modelWrap.getTeam();
            System.out.println(team);
            user.setRole("ROLE_USER");
            System.out.println(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String tname=team.getName();
            //Team team1 = this.teamRepository.getTeamByTeamName(team.getName());
            Team team1 = homeService.GetTeamname(tname);
            if (team1 == null) {
                user.setTeam(team);
            } else {
                user.setTeam(team1);
            }

            System.out.println(user);
            userRepository.save(user);
            System.out.println(user);
           
            return new ResponseEntity<>(HttpStatus.OK);

           


        } catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity<String>(HttpStatus.FOUND);
        }


    }
    /**
   	 * This method returns all details of user when the user logins into the system and shows that details on the landing page.
   	 * @param principal the principal has the object of that user who logins into the system.
   	 * @return ResponseEntity with HTTP Status .
   	 * @exception e this exception occurs when user enters wrong credentials
   	 * */
    

    @GetMapping(path = "/signIn") // /signIn
    public ResponseEntity<AuthenticationBean> basicauth(Principal principal) {
        try {

            String userName = principal.getName();


           // User user = userRepository.getUserByUserName(userName);
            User user = userControllerService.GetUserDetails(userName);
            

            ObjectMapper o = new ObjectMapper();


            String s = o.writeValueAsString(user);
            return new ResponseEntity<AuthenticationBean>(new AuthenticationBean(s), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<AuthenticationBean>(HttpStatus.UNAUTHORIZED);
        }
    }


}


	

