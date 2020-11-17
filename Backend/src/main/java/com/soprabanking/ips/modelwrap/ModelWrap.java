package com.soprabanking.ips.modelwrap;

import com.soprabanking.ips.models.Team;

import com.soprabanking.ips.models.User;
/**
 * Model Wrap Class
 * This class wraps the object of user model and team models
 * @author kavsharma
 */

public class ModelWrap {

    private User user;
    private Team team;

    /**
     * Returns the value of the {@link #user} 
     * @return the object of the user
     */
    public User getUser() {
        return user;
    }
    /**
     * Sets the value of the {@link #user}
     * @param user: an object of user
     */


    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Returns the value of the {@link #team} 
     * @return the object of the team
     */

    public Team getTeam() {
        return team;
    }

    /**
     * Sets the value of the {@link #team}
     * @param team: an object of team
     */
    public void setTeam(Team team) {
        this.team = team;
    }


    public ModelWrap() {
        super();
        // TODO Auto-generated constructor stub
    }


}
