package com.soprabanking.ips.helper;
/**
 * This is a helper class which contains the object of the password.

 * @author kavsharma
 */

import java.util.UUID;

public class Password {
	
	private UUID id;
	private String password;
	
	/**
	 * Getter method to retrieve the password of the user
	 * @return Password of the user
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * This methods sets the password in the password attribute
	 * @param password:Password of the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * To get the token ID generated for a user
	 * @return A newly created {@link UUID} object
	 */
	public UUID getId() {
		return id;
	}
	
	/**
	 * Sets the token generated for a user in the id field
	 * @param id: {@link UUID}object generated for a user
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	
}
