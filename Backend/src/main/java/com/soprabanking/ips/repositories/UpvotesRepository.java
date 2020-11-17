package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Upvotes;
/**
 * This is the Upvote Repository interface which extends the JpaRepository.It accesses the <a href="Upvotes.html">{@link com.soprabanking.ips.models.Upvotes}</a> model
 * @author mojha
 * @see com.soprabanking.ips.models.Upvotes
 */

public interface UpvotesRepository extends JpaRepository<Upvotes, Long> {
	/**
	 * This method is to find all the upvotes done on a proposal.
	 * @param proposalId : Id of the proposal we need the upvotes of
	 * @return :list of upvotes
	 */

    List<Upvotes> findAllByProposalId(Long proposalId);

    /**
     * This method is to find if a user has upvoted a proposal.
     * @param id : user id
     * @param pid :proposal id
     * @return Upvote value
     */
    Upvotes findByUserIdAndProposalId(Long id, Long pid);
}
