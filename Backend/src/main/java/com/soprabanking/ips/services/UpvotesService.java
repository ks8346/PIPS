package com.soprabanking.ips.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.JsonUtil;

import java.util.List;

/**
 * Class representing Upvotes Service that is responsible for providing the business logic for servicing the requests for Creation,Deletion and Retrieval of {@link com.soprabanking.ips.models.Upvotes} objects from the database ,received from the {@link com.soprabanking.ips.controllers.UpvotesController} 
 * <p>Upvotes Service provides a range of service methods each of which is responsible for processing various request for performing the following operations:</p>
 * <ol>
 * <li> Creating a new {@link com.soprabanking.ips.models.Upvotes} on a {@link Proposal} by a {@link User} or,</li>
 * <li> Deleting an existing {@link com.soprabanking.ips.models.Upvotes}</li>
 * <li>Determining if a {@link User} has upvoted a {@link Proposal}</li>
 * </ol>
 *<p> Service methods invokes the respective methods of {@link UpvotesDAO},{@link UserDAO} and {@link ProposalDAO} in order to return the required results to the {@link com.soprabanking.ips.controllers.UpvotesController}</p>
 * @author araghav
 * @see com.soprabanking.ips.models.Upvotes
 * @see com.soprabanking.ips.daos.UpvotesDAO
 * @see com.soprabanking.ips.daos.UserDAO
 * @see com.soprabanking.ips.daos.ProposalDAO
 * @see com.soprabanking.ips.controllers.UpvotesController
 */
@Service
public class UpvotesService {
	
	/**
	* {@link UpvotesDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link com.soprabanking.ips.models.Upvotes} objects by interacting with the persistence layer 
	*/
    @Autowired
    private UpvotesDAO upvotesDao;

    /**
	* {@link UserDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link com.soprabanking.ips.models.User} objects by interacting with the persistence layer 
	*/
    @Autowired
    private UserDAO userDao;

    /**
	* {@link ProposalDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link com.soprabanking.ips.models.Proposal} objects by interacting with the persistence layer 
	*/
    @Autowired
    private ProposalDAO proposalDao;
    private static final Logger LOGGER = LogManager.getLogger(UpvotesService.class);

    /**
     * Creates and returns a new {@link com.soprabanking.ips.models.Upvotes} object that is made by a particular {@link User} on a given {@link Proposal} according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} on which the Upvote has to be created,</li>
     * <li>id of the {@link User} who is making an Upvote on the given {@link Proposal} and,</li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal},on which the upvote is being created,id of the {@link User} who is making the upvote)
     *  ,creates a new {@link com.soprabanking.ips.models.Upvotes} object for the given {@link Proposal} made by the given {@link User} ,save the newly created {@link com.soprabanking.ips.models.Upvotes} into the database with the help of {@link UpvotesDAO#createUpvote(Upvotes)} method of {@link UpvotesDAO} and retrieves the saved {@link com.soprabanking.ips.models.Upvotes} object from the database.</p>
     * @param body String object representing the Request Body for creating a new Upvote on a given Proposal
     * @return A newly created Upvotes object 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     * @see com.soprabanking.ips.daos.UpvotesDAO#createUpvote(Upvotes)
     */
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

    /**
     * Deletes an existing {@link com.soprabanking.ips.models.Upvotes} object that is made by a particular {@link User} on a given {@link Proposal} according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} on which an existing Upvote has to be deleted,</li>
     * <li>id of the {@link User} who has made an Upvote on the given {@link Proposal} and,</li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal},on which existing upvote is being deleted and id of the {@link User} who has made the upvote)
     *  ,deletes the given {@link com.soprabanking.ips.models.Upvotes} object for the given {@link Proposal} made by the given {@link User} from the database with the help of {@link UpvotesDAO#deleteUpvote(Upvotes)} method of {@link UpvotesDAO} ,if it exists,else throws an exception.</p>
     * @param body String object representing the Request Body for deleting an existing upvote on a given Proposal 
     * @throws Exception if the Request Body is not in the correct format ,if the request parameter values are invalid or not acceptable or if the upvote to be deleted does not exist 
     * @see com.soprabanking.ips.daos.UpvotesDAO#createUpvote(Upvotes)
     */
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
	


    /**
     * Determines if a particular {@link User} has upvoted a given {@link Proposal} according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal}</li>
     * <li>id of the {@link User}</li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal} and id of the {@link User} )
     *  ,checks if the given {@link User} has made an upvote on the given {@link Proposal} with the help of {@link UpvotesDAO#getUpvoteforUserIdAndProposalId(Long, Long)} method of {@link UpvotesDAO} 
     * @param body String object representing the Request Body for checking the existence of upvote on the given Proposal made by the specified user
     * @return True,if specified {@link User} has upvoted the given {@link Proposal} and False, if the specified {@link User} has not upvoted the given {@link Proposal}
     * @throws Exception if the Request Body is not in the correct format ,if the request parameter values are invalid or not acceptable 
     * @see UpvotesDAO#getUpvoteforUserIdAndProposalId(Long, Long)
     */
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
