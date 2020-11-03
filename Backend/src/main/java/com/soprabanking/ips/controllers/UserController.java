
package com.soprabanking.ips.controllers;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/user")

public class UserController
{
	@RequestMapping("/index")
	@ResponseBody
	public String home() {
		
		return "This is Landing page of IPS";
		
	}

	
 
    

	
	
}
	

	

