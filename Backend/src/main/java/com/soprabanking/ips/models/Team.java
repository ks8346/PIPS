package com.soprabanking.ips.models;

import java.util.HashSet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.soprabanking.ips.models.User;
/**
 * This is a model class for team
 * this class implements  the information of team which is required to create a new team.
 * In this class, we are using annotation @Entity to create a table in database.
 * @author kavsharma
 */


@Entity
@Table(name = "team")
public class Team {
	/**
	 * An auto-generated unique identifier of the Team object saved in database.Denotes the primary key value of a team record in the database.
	 */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     *  Name of the team object.
     */

    private String name;

    //@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY , mappedBy="team")
    //private Set<User> users=new Set<user>();
    //private Set<User> user = new HashSet<User>();

    public Team() {
        // TODO Auto-generated constructor stub
    }
    /**
     * Returns the value of the {@link #id} field of this team
     * @return the id value of this team
     */

    public Long getId() {
        return id;
    }
    /**
     * Sets the value of the {@link #id} field of this team object using the specified id value.
     * @param id: id of long type
     */

    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Returns the value of the {@link #name} field of this team
     * @return the name value of this team
     */

    public String getName() {
        return name;
    }
    /**
     * Sets the value of the {@link #name} field of this team object using the specified name value.
     * @param name: name of string type
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns string representation of this team.
     */

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + "]";
    }

	
	

	/*public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}*/


}
