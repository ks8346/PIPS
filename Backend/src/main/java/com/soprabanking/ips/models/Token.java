package com.soprabanking.ips.models;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a model class for token
 * this class generates the token which is used during the forget password API.
 * In this class, we are using annotation @Entity to create a table in database.
 */
@Entity
@Table(name="tokens")
public class Token {

	@Id
	private UUID id;
	private String email;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
