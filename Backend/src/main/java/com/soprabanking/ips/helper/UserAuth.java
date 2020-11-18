package com.soprabanking.ips.helper;

import com.soprabanking.ips.models.Team;
/**
 * 
 * This is a helper class which contains the information of that user who logged in via social media first time.
 *@author kavsharma
 */

public class UserAuth {
	private String name;
	private String email;
	//private Team team;
	public UserAuth() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * For Retrieving the user name of the user
	 * @return name of the user
	 */
	
	public String getName() {
		return name;
	}
	
	/**
	 * Storing the name of the user
	 * @param name:Name of the user
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * For retrieving the email of the user
	 * @return email of the user
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the email of the user entered
	 * @param email:Email of the user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
