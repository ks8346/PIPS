package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findAllByProposalId(Long proposalId);
	
	List<Comment> findAllByProposalIdOrderByCreationDateDesc(Long proposalId);
	
    
}
