package com.soprabanking.ips.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Class representing a Process Improvement Proposal made by a given {@link User}.Specifies all the fields and relationships of Proposal entity in the database.
 * <p>A {@link User} can create any number of proposals.At the time of creation of a new Proposal, user has to forward it.Only the user who is creating a proposal, can forward it to one or more {@link Team} instances.
 * A proposal can also be shared by any user to any number of {@link Team} instances(to which it was not forwarded at the time of creation). A User can add {@link Comment} and {@link Upvotes} on a proposal.Also, the {@link User} ,who has created a proposal ,can delete or update it as well.</p>
 * @author araghav
 *@see <a href="User.html">User</a>
 *@see <a href="Team.html">Team</a> 
 *@see <a href="Comment.html">Comment</a>
 *@see <a href="Upvotes.html">Upvotes</a>
 */
@Entity
@Table(name = "proposals")
public class Proposal {

	/**
	 * An auto-generated unique identifier of the Proposal object saved in database.Denotes the primary key value of a proposal record in the database.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  Title String of the Proposal object.
     */
    private String title;
    
    /**
     *  Detailed description of the Proposal object.It should not be more than 1024 characters in length.
     */
    @Column(columnDefinition = "varchar(1024)")
    private String description;

    /**
     * Number of {@link Upvotes} of the Proposal object.
     * @see <a href="Upvotes.html">Upvotes</a>
     */
    @Column(columnDefinition = "bigint default 0")
    private Long upvotesCount = 0L;
    
    /**
     *  Creation Date and Time of the Proposal object.
     */
    @Column(name = "creation_date")
    private Date creationDate;
    
    /**
     * User who has created the Proposal object.Corresponds to the user_Id value of a proposal record in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
	
    /**
     * List of teams to which the Proposal object is forwarded or shared.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.PERSIST)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Team> teams;

    /**
     * Initializes a newly created Proposal object so that it represents an empty proposal.
     */
    public Proposal() {
        // TODO Auto-generated constructor stub
        teams = new HashSet<>();
    }

    /**
     * Returns the value of {@link #id} field of this Proposal
     * @return the id of this proposal 
     */
    public Long getId() {
        return id;
    }
    /**
     * Sets the value of the {@link #id} field of this Proposal using the specified id value
     * @param id value containing the id of this Proposal
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the value of {@link #title} field of this Proposal
     * @return String object containing the title of this Proposal
     */
    public String getTitle() {
        return title;
    }
 
    /**
     * Sets the value of {@link #title} field of this Proposal using the specified String value
     * @param title String containing the title which is to be given to this proposal
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the value of the {@link #description} field of this Proposal
     * @return String object containing Detailed description of this Proposal
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the {@link #description} field of this Proposal using the specified character string
     * @param description String containing the description for this proposal
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Returns the value of {@link #upvotesCount} field of this Proposal
     * @return Number of upvotes for this proposal
     */
    public Long getUpvotesCount() {
        return upvotesCount;
    }

    /**
     * Sets the value of {@link #upvotesCount} field of this Proposal using the specified count value
     * @param upvotesCount count value representing the number of upvotes for this proposal
     */
    public void setUpvotesCount(Long upvotesCount) {
        this.upvotesCount = upvotesCount;
    }

    /**
     * Returns the value of the {@link #creationDate} field of this Proposal
     * @return The Creation Date and time of this Proposal 
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of {@link #creationDate} field of this Proposal using the specified Date object 
     * @param creationDate Date object representing date and time of creation of this Proposal
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns {@link User} object that represents the user that has created this Proposal
     * @return {@link User} who has created this Proposal
     * @see <a href="User.html">User</a>
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of {@link #user} field of this Proposal using the specified {@link User} object
     * @param user {@link User} object that represents the user who is creating this Proposal
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the value of {@link #teams} field of this Proposal
     * @return A {@link Set} object that represents the teams to which this Proposal is forwarded 
     */
    public Set<Team> getTeams() {
        return teams;
    }

    /**
     * Sets the value of {@link #teams} field of this Proposal using the specified {@link Set} object
     * @param teams {@link Set} object representing the Set of teams to which this Proposal is to be forwarded
     */
    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    /**
     * Adds a {@link Team} to the {@link #teams} field of this Proposal using the specified {@link Team} object if {@link #teams} does not contain the specified Team object.
     * @param team {@link Team} object representing the team with which this Proposal is to be shared
     */
    public void addTeam(Team team) {
        if (!this.teams.contains(team)) {
            this.teams.add(team);
        }
    }

    /**
     * Returns a String representation of this Proposal
     */
    @Override
    public String toString() {
        return "Proposal [Id=" + id + ", title=" + title + ", description=" + description + ", upvotesCount="
                + upvotesCount + ", creationDate=" + creationDate + ", user=" + user
                + ", teams=" + teams + "]";
    }


}
