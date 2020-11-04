
package com.soprabanking.ips.controllers;



import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.models.User;

@RestController
@Controller
@RequestMapping("/user")

public class UserController
{
	@Autowired
    private UserRepository userRepository;
	@GetMapping("/index")
	@ResponseBody
	public User dashboard(Principal principal)
	{
		
		String userName=principal.getName();
		//get the user using username 
		
		User user=userRepository.getUserByUserName(userName);
		//String name = user.getName();
		//System.out.println("USER"+user);
	    //return "!!Welcome Landing Page !!" +"Your Informations are :" + "\n" +"userId : " + user.getId()+ "\n" +"name : " + user.getName() + "\n" + "Email : " + user.getEmail()+ "\n"  ;
	    return user;
	}
	
 
    

	
	
}
	

	

