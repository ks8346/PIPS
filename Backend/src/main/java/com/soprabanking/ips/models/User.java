package com.soprabanking.ips.models;

import java.util.Date;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * This is a model class for user
 * this class implements all the basic details of user which are required for registration purpose
 * and this class also has one to many relationship with team model.
 * In this class, we are using annotation @Entity to create a table in database.
 * @author kavsharma
 */

@Entity
@Table(name = "USERS")
public class User {
	/**
	 * An auto-generated unique identifier of the User object saved in database.Denotes the primary key value of a user record in the database.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name field is required !!")
    /**
     *  Name of the user object.
     */

    private String name;
    /**
     *  Emaid id of the User object which should be unique.
     */
    @Column(unique = true)
   
    private String email;
    /**
     *  Role of the User object.
     */
    private String role;
    @Size(min = 2, max = 20, message = "min 2 and max 20 characters are allowed !!")
    /**
     *  Password of the User object.
     */
    //@JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    
    private String password;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    @JsonIgnore
    /**
     *  Creation date and time of the User object.
     */
    private Date creationDate;
    /**
     * {@link Team}
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    
    private Team team;

    /**
     * Returns the value of the {@link #id} field of this User
     * @return the id value of this User
     */


    public Long getId() {
        return id;
    }
    /**
     * Sets the value of the {@link #id} field of this user object using the specified id value.
     * @param i: id of long type
     */

    public void setId(Long i) {
        this.id = i;
    }

    /**
     * Returns the value of the {@link #name} field of this user
     * @return the name value of this user
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the value of the {@link #name} field of this user object using the specified name value.
     * @param name: name of string type
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value of the {@link #email} field of this user
     * @return the email value of this user
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the value of the {@link #email} field of this user object using the specified email value.
     * @param email: email of string type
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the value of the {@link #password} field of this user
     * @return the password value of this User
     */

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    /**
     * Sets the value of the {@link #password} field of this user object using the specified password value.
     * @param password: password of string type
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the value of the {@link #creationDate} field of this User
     * @return the creation date and time of this user
     */
    public Date getCreationDate() {
        return creationDate;
    }
    /**
     * Sets the value of the {@link #creationDate} field of this user object.
     * @param creationDate: value containing the creation date and time of this user
     */
    public void setCreationDate(Date creationDate) {
        creationDate = creationDate;
    }

    /**
     * Returns the value of the {@link #team} field of this User
     * @return the object of the team
     */
    public Team getTeam() {
        return team;
    }
    /**
     * Sets the value of the {@link #team} field of this user object using the specified team name value.
     * @param team: object of the team
     */
    public void setTeam(Team team) {
        this.team = team;
    }
    

    public User() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Returns the value of the {@link #role} field of this User
     * @return the role value of this User
     */

    public String getRole() {
        return role;
    }
    /**
     * Sets the value of the {@link #role} field of this user object using the specified role value.
     * @param role: value containing the role of this user
     */
    public void setRole(String role) {
        this.role = role;
    }
    /**
     * Returns string representation of this user.
     */

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
                + ", CreationDate=" + creationDate + ", team=" + team + "]";
    }


}

