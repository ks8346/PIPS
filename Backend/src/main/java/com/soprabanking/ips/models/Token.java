package com.soprabanking.ips.models;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a model class for token
 * this class generates the token which is used during the forget password API.
 * In this class, we are using annotation @Entity to create a table in database.
 * @author kavsharma
 */
@Entity
@Table(name="tokens")

public class Token {
	/**
	 * An unique identifier of the token object saved in database.Denotes the primary key value of a token record in the database.
	 */

	@Id
	private UUID id;
	/**
     *  Emaid id of the token object.
     */
	private String email;
	 /**
     * Returns the value of the {@link #id} field of this token
     * @return the id value of this team
     */

	public UUID getId() {
		return id;
	}
	/**
     * Sets the value of the {@link #id} field of this token object using the specified id value.
     * @param id: id of long type
     */
	public void setId(UUID id) {
		this.id = id;
	}
	/**
     * Returns the value of the {@link #email} field of this token
     * @return the email value of this team
     */

	public String getEmail() {
		return email;
	}
	 /**
     * Sets the value of the {@link #email} field of this token object using the specified email value.
     * @param email: email of string type
     */

	public void setEmail(String email) {
		this.email = email;
	}
	
}
