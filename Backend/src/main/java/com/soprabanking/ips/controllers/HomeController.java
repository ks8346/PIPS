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
@CrossOrigin
@RestController
public class HomeController 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Autowired
	private TeamRepository teamRepository;
     
	@RequestMapping("/home")
    @ResponseBody
	public String home(Model model) {
		//model.addAttribute("title", "IPS");
		return "this is home page of IPS";
	}
	@GetMapping("/getTeam")
	public ResponseEntity<List<Object>> findAllTeams()
	{
		
		List<Object> allteam=teamRepository.getTeamIdANDName();
		allteam.forEach(e->{System.out.println(e);});
		try {
		return new ResponseEntity<>(teamRepository.getTeamIdANDName(),HttpStatus.OK );
	}
	
	catch(Exception e)
	{
		return new ResponseEntity<List<Object>>(new ArrayList<>(), HttpStatus.NOT_FOUND);
	}
}
	
	
	@PostMapping("/userRegister")
	public ResponseEntity<String> registerUser(@RequestBody ModelWrap modelWrap ) {
		
	
		
		try {
            User user=modelWrap.getUser();
            Team team= modelWrap.getTeam();
            System.out.println(team);
            user.setRole("ROLE_USER");
            System.out.println(user);
   	        user.setPassword(passwordEncoder.encode(user.getPassword()));
   	        Team team1=this.teamRepository.getTeamByTeamName(team.getName());
   	        if(team1==null)
   	        {
   	         user.setTeam(team);	
   	        }
   	        else
   	        {
   	        	user.setTeam(team1);
   	        }
   	    
	        System.out.println(user);
	        userRepository.save(user);
	        System.out.println(user);
	        //String s="Hi  " + user.getName() + "  your Registration Process successfully completed. Now Please Login to Continue";
      
 		//return ("{message:"+s+"}");
	        return new ResponseEntity<>(HttpStatus.OK );
   	        
       	    //Team team1=this.teamRepository.getTeamByTeamName(team.getName());
       	    /*if(team1==null)
       	    {
       	        user.setRole("ROLE_USER");
       	        user.setPassword(passwordEncoder.encode(user.getPassword()));
       	        user.setTeam(team);
    	        System.out.println(user);
    	        userRepository.save(user);
    	        System.out.println(user);
                String s="Hi  " + user.getName() + "  your Registration Process successfully completed. Now Please Login to Continue";
               
           		return ("{message:"+s+"}");

       	    }
       	    else
       	    {    
       	    	user.setRole("ROLE_USER");
       	        user.setPassword(passwordEncoder.encode(user.getPassword()));
       	        user.setTeam(team1);
    	        System.out.println(user);
    	        userRepository.save(user);
    	        System.out.println(user);
       	    	
           	    
           	    String s="Hi  " + user.getName() + "  your Registration Process successfully completed. Now Please Login to Continue";
              
         		return ("{message:"+s+"}");

           		//return "Hello  " + user.getName() + "  your Registration Process successfully completed" ;

    	
       	    }*/
		
       	   
		}
       	 catch (Exception e) {
 			e.printStackTrace();
 			//model.addAttribute("user", user);
 			//session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
 	
 			    //String s="Email Id already exists !!" 	;
 			   //return s;
 			return new ResponseEntity<String>(HttpStatus.FOUND);
 		     }
		
  
}
	  //handler for login page
	  /*@GetMapping("/signIn")
	  public String customLogin() {
		//model.addAttribute("title", "LogInPage - Smaeamrt Contact Manager");
		//model.addAttribute("user", new User());
		
		return "username";*/
	
	@GetMapping(path = "/signIn") // /signIn
	public ResponseEntity<AuthenticationBean> basicauth(Principal principal) {
		try {
		
		String userName=principal.getName();
        
       
        User user=userRepository.getUserByUserName(userName);
        
        ObjectMapper o=new ObjectMapper();
        
       
        	String s= o.writeValueAsString(user);
        	return new ResponseEntity<AuthenticationBean>(new AuthenticationBean(s),HttpStatus.OK );

		}
		catch(Exception e) {
			return new ResponseEntity<AuthenticationBean>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	}


	

