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
 */


@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY , mappedBy="team")
    //private Set<User> users=new Set<user>();
    //private Set<User> user = new HashSet<User>();

    public Team() {
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
