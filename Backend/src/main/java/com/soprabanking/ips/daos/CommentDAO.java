package com.soprabanking.ips.daos;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.repositories.CommentRepository;
/**
 * This class is a Data Access Object Class for Comments shown on proposals.It has the following methods
 * <ul>
 * 	<li>fetchAllComments method to fetch all the comments of a given proposal</li>
 * 	<li>createComment method to save the comment </li>
 * 	<li>deleteComment to delete a comment </li>
 * </ul>
 * @author mojha
 * <br>
 * @see org.springframework.stereotype.Component
 * @see org.springframework.beans.factory.annotation.Autowired
 */
@Component
public class CommentDAO {

    @Autowired
    private CommentRepository commentRepository;
    private static final Logger LOGGER = LogManager.getLogger(CommentDAO.class);

    /**
     * This is a method used to fetch all comments on a proposal using the proposal id in a list.
     * It uses a method findAllByProposalIdOrderByCreationDateDesc which is in the <a href="CommentRepository.html">{@link com.soprabanking.ips.repositories.CommentRepository}</a> Repository
     * @param proposalId :The Id of the proposal to which the comments belong
     * @return :A list of sorted comments
     * @see <a href="Comment.html">{@link com.soprabanking.ips.models.Comment}</a>
     * @see com.soprabanking.ips.repositories.CommentRepository
     */
    public List<Comment> fetchAllComments(Long proposalId) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": fetchAllComments() method");
        return commentRepository.findAllByProposalIdOrderByCreationDateDesc(proposalId);
    }
    
    /**
     * This method will create a comment and save it using the save method. 
     * @param com : It is a object of class Comment in the <a href="Comment.html">{@link com.soprabanking.ips.models.Comment}</a> model.
     * @return saved: It is a comment object with the saved details
     * @see <a href="Comment.html">{@link com.soprabanking.ips.models.Comment}</a>
     * @see <a href="CommentRepository.html"> {@link com.soprabanking.ips.repositories.CommentRepository}</a>
     */

    public Comment createComment(Comment com) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": createComment() method");
        Comment saved = commentRepository.save(com);
        return saved;
    }

    /**
     * This method will delete a comment by its id. 
     * @param id : It is the id of the comment
     * @see <a href="CommentRepository.html"> {@link com.soprabanking.ips.repositories.CommentRepository}</a>
     */
    public void deleteComment(Long id) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteComment() method");
        commentRepository.deleteById(id);
    }

}
