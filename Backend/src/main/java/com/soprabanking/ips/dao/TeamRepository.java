package com.soprabanking.ips.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.soprabanking.ips.entities.Team;
import com.soprabanking.ips.entities.User;

public interface TeamRepository extends JpaRepository<Team, Long> {
	//List<Team> findAll(Iterable<Long> ids);
	//public List<Team> findByName(String teamname);
	//@Query("select u FROM Teams u")
	

	//public List<Team> getAllTeam();

	//@Query("SELECT t.tid , t.teamname FROM Team t ")
	//List<Object> getTeamIdANDName();
	@Query("SELECT t.teamname FROM Team t ")
	List<Object> getTeamIdANDName();
	
	//@Query("SELECT t.teamname from Team t where t.teamname = :teamname")
	//public Team getTeamByTeamName(@Param("teamname") String teamname);
	//public List<Team> getAllteam();
	@Query("select t from Team t where t.teamname = :teamname")
	public Team getTeamByTeamName(@Param("teamname") String teamname);


	
	
}
