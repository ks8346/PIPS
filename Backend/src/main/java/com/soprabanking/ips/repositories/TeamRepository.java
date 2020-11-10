package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findById(long id);

    @Query("SELECT t.name FROM Team t ")
    List<Object> getTeamIdANDName();

    @Query("select t from Team t where t.name = :name")
    public Team getTeamByTeamName(@Param("name") String name);


}
