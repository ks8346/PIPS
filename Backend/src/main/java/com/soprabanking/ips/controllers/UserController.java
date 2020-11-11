package com.soprabanking.ips.controllers;

 

 

import java.security.Principal;
import java.util.concurrent.ThreadLocalRandom;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

 

import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.helper.UserAuth;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;

 

 


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/user")

 

public class UserController
{

 

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    
    
    @RequestMapping(value="/fbTeam", method = { RequestMethod.GET, RequestMethod.POST })
    //@RequestBody Team team,
    //(@ModelAttribute("team") Team team
    public String dashboard( Principal principal)
    {
        User user=new User();
       // Team team=new Team();
        String s=principal.toString();
        String email = s.substring(s.indexOf("email=")+6,s.indexOf(".com}];")+4);
        String name = s.substring(s.indexOf("given_name=")+11,s.indexOf(", locale"));
        int pass=ThreadLocalRandom.current().nextInt();
        String pswd=String.valueOf(pass);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pswd);
        user.setRole("ROLE_USER");
        //user.setTeam();
        User user1=this.userRepository.getUserByUserName(email);
        if(user1==null)
         
           {

              // userRepository.save(user);
              
              // return "Hiiii" + user.getName() + " and Your Team " + "  your Registration Process successfully completed" ;
                //return "False";
        	//return "success  " + email+ name;
        	return "{"+"\n"+"email :"+email+","+"\n"+"name :"+name+","+"\n"+"team : null"+"\n"+"}";
  
           }
           
//           else if(user1.getTeam()==null && user1!=null)
//        {
//
//           return "Hello" + user1.getName() + " and Yout Team " + user1.getTeam()+"  your Registration Process successfully completed" ;
//
// 
//        }
           else 
           {
               return "this is your landing Page and You are Social User";
           }
    
           
        
       

 

        
       
        
        
    }
    
    @GetMapping("/index")
    @ResponseBody
    public  String indexPage(Principal principal)
    {
        
        //String userName=principal.getName();
        //get the user using username 
        
        //User user=userRepository.getUserByUserName(userName);
        //String name = user.getName();
        //System.out.println("USER"+user);
        //return "!!Welcome Landing Page !!" +"Your Informations are :" + "\n" +"userId : " + user.getId()+ "\n" +"name : " + user.getName() + "\n" + "Email : " + user.getEmail()+ "\n"  ;
        return "You are normal user so this is your landing page";
    }
    
    
    @PostMapping("/getSocialInfo")
    @ResponseBody
    public  ResponseEntity<UserAuth> getSocialInfo(@RequestBody UserAuth userAuth)
    {
    	
    	//String email=userAuth.getEmail();
    	 //User user=new User();
         // Team team=new Team();
         // String s=principal.toString();
          String email =userAuth.getEmail();

        
          User user1=this.userRepository.getUserByUserName(email);
             
    	 
          if(user1==null)
           
             {
        	  

        	  
                // userRepository.save(user);
                
                // return "Hiiii" + user.getName() + " and Your Team " + "  your Registration Process successfully completed" ;
                  //return "False";
          	//return "success  " + email+ name;
          	//return "{"+"\n"+"email :"+userAuth.getEmail()+","+"\n"+"name :"+userAuth.getName()+","+"\n"+"team : null"+"\n"+"}";
        	  return new ResponseEntity<>(userAuth,HttpStatus.NOT_FOUND);
             }
             
//             else if(user1.getTeam()==null && user1!=null)
//          {
  //
//             return "Hello" + user1.getName() + " and Yout Team " + user1.getTeam()+"  your Registration Process successfully completed" ;
  //
  // 
//          }
             else 
             {
            	 //responseMessage.setTeam(user1.getTeam());
                 return new ResponseEntity<>(userAuth, HttpStatus.FOUND);
             }
    	
        
        //String userName=principal.getName();
        //get the user using username 
        
        //User user=userRepository.getUserByUserName(userName);
        //String name = user.getName();
        //System.out.println("USER"+user);
        //return "!!Welcome Landing Page !!" +"Your Informations are :" + "\n" +"userId : " + user.getId()+ "\n" +"name : " + user.getName() + "\n" + "Email : " + user.getEmail()+ "\n"  ;
       // return "You are normal user so this is your landing page";
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
    

