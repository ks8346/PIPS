package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Comment;
/**
 * This is the Comment Repository interface which extends the JpaRepository.It accesses the <a href="Comment.html">{@link com.soprabanking.ips.models.Comment}</a> model
 * @author mojha
 * @see com.soprabanking.ips.models.Comment
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
	/**
	 * 	This method is used to find all the comments made on a proposal by the proposal id 
	 * @param proposalId :The Id of the proposal to which the comments belong
	 * @return A list of Comments
	 */

    List<Comment> findAllByProposalId(Long proposalId);
    /**
     * This method is used to find all the comments made on a proposal by the proposal id and sort those proposals on the basis of the creation date in a descending order. 
     * @param proposalId :The Id of the proposal to which the comments belong
     * @return A list of sorted comments
     */

    List<Comment> findAllByProposalIdOrderByCreationDateDesc(Long proposalId);


}
