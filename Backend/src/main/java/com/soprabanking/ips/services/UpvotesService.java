package com.soprabanking.ips.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.JsonUtil;

import java.util.List;

@Service
public class UpvotesService {
    @Autowired
    private UpvotesDAO upvotesDao;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private ProposalDAO proposalDao;
    private static final Logger LOGGER = LogManager.getLogger(UpvotesService.class);

    public Upvotes upvoteProposal(String body) throws Exception {
        try {
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": upvoteProposal() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            Long uid = Long.parseLong(jsonObj.get("userId").asText());
            Upvotes upvotes = upvotesDao.getUpvoteforUserIdAndProposalId(uid, pid);
            if (upvotes != null) {
                LOGGER.info("Inside "+this.getClass().getSimpleName()+": upvoteProposal() SUCCESS");
                return upvotes;
            } else {
                User user = userDao.getById(uid);

                Upvotes upvote = new Upvotes();
                Upvotes upvoted;
                if (user != null) {
                    Proposal proposal = proposalDao.getById(pid);
                    if (proposal != null) {
                        upvote.setProposal(proposal);
                        upvote.setUser(user);

                        proposal.setUpvotesCount(proposal.getUpvotesCount() + 1);
                        proposalDao.saveProposal(proposal);

                        upvoted = upvotesDao.createUpvote(upvote);
                    } else {
                        throw new Exception();
                    }
                } else {
                    throw new Exception();
                }
                LOGGER.info("Inside "+this.getClass().getSimpleName()+": upvoteProposal() SUCCESS");
                return upvoted;
            }
        } catch (Exception ex) {
            LOGGER.error("Inside "+this.getClass().getSimpleName()+": upvoteProposal() FAILURE",ex);
            throw new Exception();
        }
    }

    public void reverseUpvote(String body) throws Exception {
        try {
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": reverseUpvote() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            Long uid = Long.parseLong(jsonObj.get("userId").asText());
            Upvotes upvote = upvotesDao.getUpvoteforUserIdAndProposalId(uid, pid);
            if (upvote != null) {
                Proposal proposal = proposalDao.getById(pid);
                if (proposal.getUpvotesCount() >= 1)
                    proposal.setUpvotesCount(proposal.getUpvotesCount() - 1);
                proposalDao.saveProposal(proposal);
                upvotesDao.deleteUpvote(upvote);
                LOGGER.info("Inside "+this.getClass().getSimpleName()+": reverseUpvote() SUCCESS");
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            LOGGER.error("Inside "+this.getClass().getSimpleName()+": reverseUpvote() FAILURE",e);
            throw new Exception();
        }
    }
	
	/*public List<Upvotes> listUpvotes(Long proposalId)
	{
		try {
		return upvotesDao.fetchAllUpvotes(proposalId);
		}
		catch(Exception ex)
		{
			return null;
		}
	}*/

    public boolean hasUpvoted(String body) throws Exception {
        try {
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": hasUpvoted() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            Long uid = Long.parseLong(jsonObj.get("userId").asText());
            Upvotes upvote = upvotesDao.getUpvoteforUserIdAndProposalId(uid, pid);
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": hasUpvoted() SUCCESS");
            if (upvote != null) {
                return true;
            } else {
                return false;
            }


        } catch (Exception e) {
            LOGGER.error("Inside "+this.getClass().getSimpleName()+": hasUpvoted() FAILURE",e);
            throw new Exception();
        }
    }


	/*public List<Upvotes> listUpvotes(Long proposalId) {
		return upvotesDao.fetchAllUpvotes(proposalId);
	}*/
}
