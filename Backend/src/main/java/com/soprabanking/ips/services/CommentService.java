package com.soprabanking.ips.services;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.JsonUtil;

/**
 * Class representing Comment Service that is responsible for providing the business logic for servicing the requests for Creation,Deletion and Retrieval of {@link Comment} objects from the database ,received from the {@link com.soprabanking.ips.controllers.CommentController} 
 * <p>Comment Service provides a range of service methods each of which is responsible for processing various request for performing the following operations:</p>
 * <ol>
 * <li> Fetching list of all the comments for a given Proposal</li>
 * <li> Creating a new {@link Comment} on a {@link Proposal} by a {@link User} or,</li>
 * <li> Deleting an existing {@link Comment}</li>
 * </ol>
 *<p> Service methods invokes the respective methods of {@link CommentDAO},{@link UserDAO} and {@link ProposalDAO} in order to return the required results to the {@link com.soprabanking.ips.controllers.CommentController}</p>
 * @author araghav
 * @see com.soprabanking.ips.models.Comment
 * @see com.soprabanking.ips.daos.CommentDAO
 * @see com.soprabanking.ips.daos.UserDAO
 * @see com.soprabanking.ips.daos.ProposalDAO
 * @see com.soprabanking.ips.controllers.CommentController
 */
@Service
public class CommentService {
   /**
    * {@link CommentDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Comment} objects by interacting with the persistence layer 
    */
	@Autowired
    private CommentDAO commentDao;

	/**
	* {@link UserDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link User} objects by interacting with the persistence layer. 
    */
    @Autowired
    private UserDAO userDao;

    /**
     * {@link ProposalDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Proposal} objects by interacting with the persistence layer.
     */
    @Autowired
    private ProposalDAO proposalDao;

    private static final Logger LOGGER = LogManager.getLogger(CommentService.class);


    /**
 
     * Returns a List of {@link Comment} objects of a given {@link Proposal} in response to the specified Request Body String containing the id value of the {@link Proposal}  
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal},the comments of which have to be fetched from the database)
     *  and then retrieves the list of comments for the given {@link Proposal} having the given id value with the help of {@link CommentDAO#fetchAllComments(Long)} method of {@link CommentDAO}. </p>
     * @param body String object representing the Request Body for fetching the List of Comments for a given Proposal
     * @return List of {@link Comment} objects of a given {@link Proposal}
     * @throws Exception if the Request Body is not in the correct format or if the Proposal id value is invalid or not acceptable
     * @see com.soprabanking.ips.daos.CommentDAO#fetchAllComments(Long)
     */
    public List<Comment> displayComments(String body) throws Exception {
        try {
            LOGGER.info("Inside " + this.getClass().getSimpleName() + ": displayComments() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long proposalId = Long.parseLong(jsonObj.get("id").asText());

            List<Comment> result = commentDao.fetchAllComments(proposalId);
            LOGGER.info("Inside " + this.getClass().getSimpleName() + ": displayComments() SUCCESS");
            return !result.isEmpty() ? result : null;
        } catch (Exception ex) {
            LOGGER.error("Inside " + this.getClass().getSimpleName() + ": displayComments() FAILURE",ex);
            throw new Exception();
        }
    }

    /**
     * Creates and returns a new {@link Comment} object that is made by a particular {@link User} on a given {@link Proposal} according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} on which the Comment has to be created,</li>
     * <li>id of the {@link User} who is making a comment on the given {@link Proposal} and,</li>
     * <li>Text of the {@link Comment}.</li></ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal},on which the comment is being created,id of the {@link User} who is making the comment,and the text of the comment.)
     *  ,creates a new {@link Comment} object containing a textual comment for the given {@link Proposal} made by the given {@link User} on current date and time ,save the newly created {@link Comment} into the database with the help of {@link CommentDAO#createComment(Comment)} method of {@link CommentDAO} and retrieves the saved {@link Comment} object from the database.</p>
     * @param body String object representing the Request Body for creating a new comment for a given Proposal
     * @return A newly created Comment object 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     * @see com.soprabanking.ips.daos.CommentDAO#createComment(Comment)
     */
    public Comment addComment(String body) throws Exception {
        try {
            LOGGER.info("Inside " + this.getClass().getSimpleName() + ": addComment() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            String text = jsonObj.get("text").asText();
            Long uid = Long.parseLong(jsonObj.get("userId").asText());
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            User user = userDao.getById(uid);
            Comment comment = new Comment();
            Comment addedComment;
            if (user != null) {
                Proposal proposal = proposalDao.getById(pid);
                if (proposal != null) {
                    comment.setComment(text);
                    comment.setUser(user);
                    comment.setProposal(proposal);
                    comment.setCreationDate(new Date());
                    addedComment = commentDao.createComment(comment);
                } else {
                    throw new Exception();
                }
            } else {
                throw new Exception();
            }
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": addComment() SUCCESS");
            return addedComment;

        } catch (Exception e) {
            LOGGER.error("Inside "+this.getClass().getSimpleName()+": addComment() FAILURE",e);
            throw new Exception();
        }


    }

    /**
     * Deletes an existing {@link Comment} object in response to the specified Request Body String containing the id value of the {@link Comment} object to be deleted. 
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Comment} which has to be deleted.)
     *  and then deletes the {@link Comment} object having the given id value with the help of {@link CommentDAO#deleteComment(Long)} method of {@link CommentDAO}. </p>
     * @param body String object representing the Request Body for deleting an existing comment for a given Proposal.
     * @throws Exception if the Request Body is not in the correct format or if the id value of {@link Comment} is invalid or not acceptable
     * @see com.soprabanking.ips.daos.CommentDAO#deleteComment(Long)
     */
    public void deleteComment(String body) throws Exception {

        try {
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteComment() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);

            Long id = Long.parseLong(jsonObj.get("id").asText());

            commentDao.deleteComment(id);
            LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteComment() SUCCESS");

        } catch (Exception ex) {
            LOGGER.error("Inside "+this.getClass().getSimpleName()+": deleteComment() FAILURE",ex);
            throw new Exception();
        }
    }


}
