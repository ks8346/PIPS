package com.soprabanking.ips.daos;

import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.TeamRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamDAO {

    @Autowired
    TeamRepository teamRepository;

    public Team getTeam(long id) {
        return teamRepository.findById(id);
    }

    public List<Team> fetchAllTeams() {

        return teamRepository.findAll();
    }
}
