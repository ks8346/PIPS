package com.soprabanking.ips;

import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.soprabanking.ips.dao.TeamRepository;
import com.soprabanking.ips.dao.UserRepository;
import com.soprabanking.ips.entities.Team;
import com.soprabanking.ips.entities.User;

@SpringBootApplication
public class ProposalsystemApplication {

	public static void main(String[] args) {
		 
		ApplicationContext context = SpringApplication.run(ProposalsystemApplication.class, args);
		
		//UserRepository userRepository=context.getBean(UserRepository.class);
		
		//List<User> alluser = userRepository.findByName("jonty");
		//alluser.forEach(e->System.out.println(e));
		
		/*List<User> allteam = userRepository.getAlluser();
		allteam.forEach(e->{
			System.out.println(e);
		});*/
		
		/*TeamRepository teamRepository=context.getBean(TeamRepository.class);
		
		List<Team> allteam = teamRepository.getAllteam();
		allteam.forEach(e->{
			System.out.println(e);
		});*/
		
		
		

//userRepository.getUsers().forEach(e->System.out.println(e));

}
}
