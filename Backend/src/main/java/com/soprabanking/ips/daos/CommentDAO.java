package com.soprabanking.ips.daos;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.repositories.CommentRepository;

@Component
public class CommentDAO {

    @Autowired
    private CommentRepository commentRepository;
    private static final Logger LOGGER = LogManager.getLogger(CommentDAO.class);

    public List<Comment> fetchAllComments(Long proposalId) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": fetchAllComments() method");
        return commentRepository.findAllByProposalIdOrderByCreationDateDesc(proposalId);
    }

    public Comment createComment(Comment com) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": createComment() method");
        Comment saved = commentRepository.save(com);
        return saved;
    }

    public void deleteComment(Long id) {
        LOGGER.info("Inside "+this.getClass().getSimpleName()+": deleteComment() method");
        commentRepository.deleteById(id);
    }

}
