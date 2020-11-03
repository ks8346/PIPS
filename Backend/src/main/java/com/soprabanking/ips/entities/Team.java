package com.soprabanking.ips.entities;


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
//import org.hibernate.mapping.Set;



@Entity
@Table(name="TEAM")
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tid;
	private String teamname;
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY , mappedBy="team")
	//private Set<User> users=new Set<user>();
	private Set<User> user = new HashSet<User>();
	
	

	public Long getTid() {
		return tid;
	}



	public void setTid(Long tid) {
		this.tid = tid;
	}



	public String getTeamname() {
		return teamname;
	}



	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}




	public Set<User> getUser() {
		return user;
	}



	public void setUser(Set<User> user) {
		this.user = user;
	}



	public Team() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "Team [tid=" + tid + ", teamname=" + teamname + ", user=" + user + "]";
	}
	
	
	
	
	
	
	
}
//,mappedBy = "user"
