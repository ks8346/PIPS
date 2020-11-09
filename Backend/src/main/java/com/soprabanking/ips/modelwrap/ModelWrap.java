package com.soprabanking.ips.modelwrap;

import com.soprabanking.ips.models.Team;


import com.soprabanking.ips.models.User;

public class ModelWrap {

    private User user;
    private Team team;


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Team getTeam() {
        return team;
    }


    public void setTeam(Team team) {
        this.team = team;
    }


    public ModelWrap() {
        super();
        // TODO Auto-generated constructor stub
    }


}
