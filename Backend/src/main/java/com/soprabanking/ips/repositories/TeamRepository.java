package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.Team;
/**
 * This is a repository interface
 * this interface extends the JPA repository which is used for fetching the team data from the database.
 * and in this interface, we fire some queries to fetch the data from the database
 * 
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findById(long id);
    /**
	 * This method returns the list of the object of team by firing the custom query.
	 */


    @Query("SELECT t.name FROM Team t ")
    List<Object> getTeamIdANDName();
    /**
	 * This method fetches the details of the team by firing the custom query.
	 * @param name team name
	 */


    @Query("select t from Team t where t.name = :name")
    public Team getTeamByTeamName(@Param("name") String name);


}
