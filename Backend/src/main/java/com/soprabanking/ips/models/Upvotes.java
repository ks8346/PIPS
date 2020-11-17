package com.soprabanking.ips.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Class representing an upvote made by a {@link User} on a given {@link Proposal}.Specifies all the fields and relationships of Upvote entity in the database.
 * <p>A particular {@link User} can make only one upvote on a given {@link Proposal} by liking that {@link Proposal}.Once upvoted by a particular {@link User},a particular {@link Proposal} cannot be reupvoted by the same {@link User}.A {@link Proposal} can have any number of upvotes.An upvote made by a particular {@link User} can be deleted either explicitly({@link User},who has liked the {@link proposal},dislikes it) or 
 * implicitly(either {@link User} who has made the upvote is removed from the system or the {@link Proposal} for which upvote has been created is deleted from the database)</p>
 * @author araghav
 *@see <a href="User.html">User</a>
 *@see <a href="Proposal.html">Proposal</a>
 */
@Entity
@Table(name = "upvotes")
public class Upvotes {

	/**
	 * An auto-generated unique identifier of the Upvotes object saved in database.Denotes the primary key value of an upvote record in the database.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * {@link User} who has made the Upvote on a {@link Proposal}.Corresponds to the user_Id value of an upvote record in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.PERSIST)
    private User user;

    /**
     * {@link Proposal} for which the Upvote has been made by a {@link User} .Corresponds to the proposal_Id value of an upvote record in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id", nullable = false)
    @Cascade(CascadeType.PERSIST)
    private Proposal proposal;

    /**
     * Initializes a newly created Upvotes object so that it represents an empty upvote.
     */
    public Upvotes() {
        // TODO Auto-generated constructor stub
    }


    /**
     * Returns the value of {@link #id} field of this Upvotes object.
     * @return The id value of this Upvotes object.
     */
    public Long getId() {
        return id;
    }


    /**
     * Sets the value of {@link #id} field of this Upvotes object using the specified id value
     * @param id value containing the id of this Upvotes object
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns {@link User} object that represents the user that has created this Upvotes object
     * @return {@link User} who has created this upvote
     * @see <a href="User.html">User</a>
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of {@link #user} field of this Upvotes object using the specified {@link User} object
     * @param user {@link User} object that represents the user who is creating this upvote
     */
    public void setUser(User user) {
        this.user = user;
    }


    /**
     * Returns {@link Proposal } object that represents the proposal for which this Upvotes object has been created
     * @return Proposal for which this upvote is created
     * @see <a href="Proposal.html">Proposal</a>
     */
    public Proposal getProposal() {
        return proposal;
    }


    /**
     * Sets the value of {@link #proposal} field of this Upvotes object using the specified {@link Proposal} object
     * @param proposal {@link Proposal} object that represents the proposal for which this upvote
     *  is being created
     */
    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    /**
     * Returns a String representation of this Upvotes object.
     */
    @Override
    public String toString() {
        return "Upvotes [id=" + id + ", user=" + user + ", proposal=" + proposal + "]";
    }


}
