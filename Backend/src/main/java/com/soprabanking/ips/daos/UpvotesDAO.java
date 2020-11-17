package com.soprabanking.ips.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.repositories.UpvotesRepository;
/**
 * This class is a Data Access Object Class for <a href="Upvotes.html">{@link com.soprabanking.ips.models.Upvotes}</a> made on proposals.It has the following methods
 * <ul>
 * 	<li>createUpvote</li>
 * 	<li>fetchAllUpvotes</li>
 * 	<li>deleteUpvote</li>
 * 	<li>getUpvoteforUserIdAndProposalId</li>
 * </ul>
 * @author mojha
 *
 */
@Component
public class UpvotesDAO {
    @Autowired
    private UpvotesRepository upvotesRepository;
    private static final Logger LOGGER = LogManager.getLogger(UpvotesDAO.class);
/**
 * This method is used to create an upvote on a proposal
 * @param upvote :An object of <a href="Upvote.html">{@link com.soprabanking.ips.models.Upvote}</a>
 * @return saved upvote
 */
    public Upvotes createUpvote(Upvotes upvote) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": createUpvote() method");
        return upvotesRepository.save(upvote);
    }
    /**
     * This method is used to fetch all the upvotes on a proposal
     * @param proposalId :Id of the proposal 
     * @return :list of upvotes done on proposal
     */

    public List<Upvotes> fetchAllUpvotes(Long proposalId) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": fetchAllUpvotes() method");
        return upvotesRepository.findAllByProposalId(proposalId);
    }
    /**
     * This method is used to delete an upvote
     * @param upvote: the upvote object that needs to be deleted
     */

    public void deleteUpvote(Upvotes upvote) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteUpvote() method");
        upvotesRepository.delete(upvote);

    }
    /**
     * This method is to check if a user has upvoted a proposal
     * @param uid : User id
     * @param pid : proposal Id
     * @return upvote value
     */

    public Upvotes getUpvoteforUserIdAndProposalId(Long uid, Long pid) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": getUpvoteforUserIdAndProposalId() method");
        return upvotesRepository.findByUserIdAndProposalId(uid, pid);
    }


}
