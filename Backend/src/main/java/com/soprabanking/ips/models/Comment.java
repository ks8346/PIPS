package com.soprabanking.ips.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * Class representing a comment made by a {@link User} on a given {@link Proposal}.Specifies all the fields and relationships of Comment entity in the database.
 * <p>A {@link User} can make any number of comments on a particular {@link Proposal}.A {@link Proposal} can have any number of comments.A comment can be deleted either explicitly({@link User},who has created the comment ,deletes it) or 
 * implicitly(either {@link User} who has made the comment is removed from the system or the {@link Proposal} for which comment has been created is deleted from the database)</p>
 * @author araghav
 *@see <a href="User.html">User</a>
 *@see <a href="Proposal.html">Proposal</a>
 */
@Entity
@Table(name = "comments")
public class Comment {

	/**
	 * An auto-generated unique identifier of the Comment object saved in database.Denotes the primary key value of a comment record in the database.
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     *  Text String of the Comment object.
     */
    private String comment;
    
    /**
     * Creation Date and time of the Comment object.
     */
    private Date creationDate;

    
    /**
     * {@link User} who has made the Comment object.Corresponds to the user_Id value of a comment record in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Cascade(CascadeType.PERSIST)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    /**
     * {@link Proposal} for which the Comment object is created.Corresponds to the proposal_Id value of a comment record in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proposal_id", nullable = false)
    @Cascade(CascadeType.PERSIST)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Proposal proposal;

    /**
     * Initializes a newly created Comment object so that it represents an empty comment.
     */
    public Comment() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Returns the value of the {@link #id} field of this Comment
     * @return the id value of this Comment
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of the {@link #id} field of this Comment object using the specified id value.
     * @param id value containing the id of this Comment
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the value of the {@link #comment} field of this Comment
     * @return String representing the title of this Comment object.
     */
    public String getComment() {
        return comment;
    }
    /**
     * Sets the value of {@link #comment} field of this Comment object using the specified String value. 
     * @param comment String containing the text to be commented 
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns the value of the {@link #creationDate} field of this Comment
     * @return The Creation Date and time of this Comment 
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the value of the {@link #creationDate} field of this Comment using the specified Date object
     * @param creationDate Date object representing date and time of creation of this Comment 
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Returns {@link User} object that represents the user that has created this Comment
     * @return {@link User}  who has created this Comment
     * @see <a href="User.html">User</a>
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of {@link #user} field of this Comment using the specified {@link User} object
     * @param user {@link User} object that represents the user who is creating this comment
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns {@link Proposal} object that represents the proposal for which this Comment has been created
     * @return {@link Proposal} for which this comment is created
     * @see <a href="Proposal.html">Proposal</a>
     */
    public Proposal getProposal() {
        return proposal;
    }

    /**
     * Sets the value of {@link #proposal} field of this Comment using the specified {@link Proposal} object
     * @param proposal {@link Proposal} object that represents the proposal for which this comment is being created
     */
    public void setProposal(Proposal proposal) {
        this.proposal = proposal;
    }

    /**
     * Returns string representation of this Comment.
     */
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", creationDate=" + creationDate +
                ", user=" + user +
                ", proposal=" + proposal +
                '}';
    }
}
