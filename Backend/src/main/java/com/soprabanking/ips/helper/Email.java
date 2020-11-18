package com.soprabanking.ips.helper;
/**
 * This is a helper class which contains the object of the email.
 * @author kavsharma
 */

public class Email {
	
	private String mail;
	
	/**
	 * To retrieve a mailing address of the user.
	 * @return a String mail address
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * Sets the email address of the user who is too receive the mail. 
	 * @param mail:String email address
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}
