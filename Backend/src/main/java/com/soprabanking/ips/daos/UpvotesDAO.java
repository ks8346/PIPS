package com.soprabanking.ips.daos;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.repositories.UpvotesRepository;

@Component
public class UpvotesDAO {
    @Autowired
    private UpvotesRepository upvotesRepository;
    private static final Logger LOGGER = LogManager.getLogger(UpvotesDAO.class);

    public Upvotes createUpvote(Upvotes upvote) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": createUpvote() method");
        return upvotesRepository.save(upvote);
    }

    public List<Upvotes> fetchAllUpvotes(Long proposalId) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": fetchAllUpvotes() method");
        return upvotesRepository.findAllByProposalId(proposalId);
    }

    public void deleteUpvote(Upvotes upvote) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteUpvote() method");
        upvotesRepository.delete(upvote);

    }

    public Upvotes getUpvoteforUserIdAndProposalId(Long uid, Long pid) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": getUpvoteforUserIdAndProposalId() method");
        return upvotesRepository.findByUserIdAndProposalId(uid, pid);
    }


}
