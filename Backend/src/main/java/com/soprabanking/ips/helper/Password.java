package com.soprabanking.ips.helper;
/**
 * This is a helper class which contains the object of the password.
 */

import java.util.UUID;

public class Password {
	private UUID id;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	
}
