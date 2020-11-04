package com.soprabanking.ips.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;

@Component
public class UserDAO
{
	@Autowired
    private UserRepository userRepository;
	
	public User getById(Long id)
	{
		return userRepository.getOne(id);
	}
 
}
