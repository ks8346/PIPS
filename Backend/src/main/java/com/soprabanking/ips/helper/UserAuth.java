package com.soprabanking.ips.helper;

import com.soprabanking.ips.models.Team;
/**
 * 
 * This is a helper class which contains the information of that user who logged in via social media first time.
 *
 */

public class UserAuth {
	private String name;
	private String email;
	//private Team team;
	public UserAuth() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
