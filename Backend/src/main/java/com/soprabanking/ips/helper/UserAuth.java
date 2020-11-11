package com.soprabanking.ips.helper;

public class UserAuth {
	private String name;
	private String email;
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
	@Override
	public String toString() {
		return "UserAuth [name=" + name + ", email=" + email + "]";
	}
	

}
