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

@Entity
@Table(name = "proposals")
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(columnDefinition = "varchar(1024)")
    private String description;

    @Column(columnDefinition = "bigint default 0")
    private Long upvotesCount = 0L;
    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
	
	/*@OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "proposal_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Set<Comment> comments;*/

    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade(CascadeType.PERSIST)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Team> teams;


    public Proposal() {
        // TODO Auto-generated constructor stub
        teams = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUpvotesCount() {
        return upvotesCount;
    }

    public void setUpvotesCount(Long upvotesCount) {
        this.upvotesCount = upvotesCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
/*
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}*/

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void addTeam(Team team) {
        if (!this.teams.contains(team)) {
            this.teams.add(team);
        }
    }

    @Override
    public String toString() {
        return "Proposal [Id=" + id + ", title=" + title + ", description=" + description + ", upvotesCount="
                + upvotesCount + ", creationDate=" + creationDate + ", user=" + user
                + ", teams=" + teams + "]";
    }


}
