package com.soprabanking.ips.controllers;


import java.security.Principal;





import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.soprabanking.ips.entities.ModelWrap;
import com.soprabanking.ips.entities.Team;
import com.soprabanking.ips.entities.User;
import com.soprabanking.ips.helper.Message;
import com.soprabanking.ips.dao.TeamRepository;
import com.soprabanking.ips.dao.UserRepository;
@CrossOrigin(origins="http://localhost:4200")
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
	public List<Object> findAllTeams()
	{
		List<Object> allteam=teamRepository.getTeamIdANDName();
		allteam.forEach(e->{System.out.println(e);});
		return teamRepository.getTeamIdANDName();
	}
	
	
	@PostMapping("/userRegister")
	public String registerUser(@RequestBody ModelWrap modelWrap ) {
		
		try {
            User user=modelWrap.getUser();
            Team team= modelWrap.getTeam();
       	    Team team1=this.teamRepository.getTeamByTeamName(team.getTeamname());
       	    if(team1==null)
       	    {
       	        user.setRole("ROLE_USER");
                userRepository.save(user);
       	    	user.setTeam(team);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
               
 	    	    team.getUser().add(user);
 	    	    this.teamRepository.save(team);
                String s="Hi  " + user.getName() + "  your Registration Process successfully completed. Now Please Login to Continue";
               
           		return ("{message:"+s+"}");

       	    }
       	    else
       	    {   
       	    	user.setPassword(passwordEncoder.encode(user.getPassword()));
       	        user.setRole("ROLE_USER");
       	    	user.setTeam(team1);
       	        userRepository.save(user);
           	    team1.getUser().add(user);
           	    this.teamRepository.save(team1);
           	    String s="Hi  " + user.getName() + "  your Registration Process successfully completed. Now Please Login to Continue";
              
         		return ("{message:"+s+"}");

           		//return "Hello  " + user.getName() + "  your Registration Process successfully completed" ;

    	
       	    } }
		
       	    
       	 catch (Exception e) {
 			e.printStackTrace();
 			//model.addAttribute("user", user);
 			//session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
 	
 			    String s="Email Id already exists !!" 	;
 			   return ("{message:"+s+"}");
 		     }
  
}
	  //handler for login page
	  @GetMapping("/signIn")
	  public String customLogin() {
		//model.addAttribute("title", "LogInPage - Smaeamrt Contact Manager");
		//model.addAttribute("user", new User());
		
		return "username";
	}
}


	

