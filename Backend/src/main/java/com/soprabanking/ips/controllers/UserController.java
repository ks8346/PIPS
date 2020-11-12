package com.soprabanking.ips.controllers;

import java.security.Principal;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD

=======
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.authentication.AuthenticationBean;
>>>>>>> bb7d67d05a0071b271d9049b7f6ac2badc13c7f2
import com.soprabanking.ips.helper.UserAuth;
import com.soprabanking.ips.models.User;
<<<<<<< HEAD
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
=======


 

 
>>>>>>> bb7d67d05a0071b271d9049b7f6ac2badc13c7f2


@CrossOrigin(origins="http://localhost:4200")
@RestController
<<<<<<< HEAD
@RequestMapping("/user")
=======
//@RequestMapping("/user")
/**
 * Provides Rest-APIs for logging in through social media .
 *
 * <p>
 * This is a User Controller Class which implements social media handler(getSocialInfo rest API) and with this handler, 
 * user can access our landing page via social media(GMAIL).
 */

 

>>>>>>> bb7d67d05a0071b271d9049b7f6ac2badc13c7f2
public class UserController
{

 

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    /**
   	 * This method verifies that the user exists in our database or not.
   	 * if the user already exists then it returns all the information of that user and redirects to landing page.
   	 * else user redirects to create team page for registration.
   	 * @param  the userAuth has the information of that user who logged in via social media.
   	 * @return ResponseEntity with HTTP Status .
   	 * @exception e this exception occurs when user is unauthorized.
   	 * */
  
    @PostMapping("/getSocialInfo")
    @ResponseBody
    public  ResponseEntity<String> getSocialInfo(@RequestBody UserAuth userAuth)
    {
    	System.out.println("hiii");
    	
    	String email=userAuth.getEmail();
    	

        
          User user1=this.userRepository.getUserByUserName(email);
        
          if(user1==null)
           
             {
        	  

        	  
               
          	String s="{"+"\n"+"email :"+userAuth.getEmail()+","+"\n"+"name :"+userAuth.getName()+","+"\n"+"team : null"+"\n"+"}";
        	  return new ResponseEntity<String>(s,HttpStatus.NOT_FOUND);
             }
     
             else 
             {
            	 
            	        try {

            	           
            	        	
            	             String msg="{"+"\n"+"id :"+user1.getId()+","+"\n"+"name :"+user1.getName()+","+"\n"+"email :"+user1.getEmail()+","+"\n"+"role :"+user1.getRole()+","+"\n"+"team : {"+"\n"+"id :"+user1.getTeam().getId()+","+"\n"+"name :"+user1.getTeam().getName()+"}"+"\n"+"}";
            	            return new ResponseEntity<String>(msg, HttpStatus.OK);

            	        } catch (Exception e) {
            	            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            	        }
            	    }

            	
             }
          
    	
        
       
    }
    

 

    
 
    

 

    
    


 


/*
 *  Team team1=this.teamRepository.getTeamByTeamName(team.getTeamname());
           User user1=this.userRepository.getUserByUserName(email);
           if(team1==null || user1==null)
           {
  
               user.setTeam(team);
            userRepository.save(user);
            team.getUser().add(user);
           // user.setPassword(passwordEncoder.encode(user.getPassword()));
           
               
            this.teamRepository.save(team);
            String response ="Hello  " + user.getName() + "  your Registration Process successfully completed";
           
               return("{message:"+response+"}") ;

 

           }
           else if(team1!=null || user==null)
           {   
               //user.setPassword(passwordEncoder.encode(user.getPassword()))
               user.setTeam(team1);
               userRepository.save(user);
               team1.getUser().add(user);
               this.teamRepository.save(team1);  
               return "Hello  " + user.getName() + "  your Registration Process successfully completed" ;

 

    
           } 
           else 
           {
               return "this is your landing Page";
           }
 */

 

/*
 * //String userName=principal.getName();
        //get the user using username 
        
        //User user=userRepository.getUserByUserName(userName);
        //String name = user.getName();
        //System.out.println("USER"+user);
        //return "!!Welcome Landing Page !!" + "\n" +"userId : " + user.getId()+ "\n" +"name : " + user.getName() + "\n" + "Email : " + user.getEmail()+ "\n" 
        //+"TeamId : " + user.getTeam().getTid() + "\n" + "Teamname : " + user.getTeam().getTeamname()  ;
        System.out.println(name);
        System.out.println(user);
        return principal;
 */
    

