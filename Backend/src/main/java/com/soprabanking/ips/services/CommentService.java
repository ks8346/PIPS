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

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDao;

    @Autowired
    private UserDAO userDao;

    @Autowired
    private ProposalDAO proposalDao;

    private static final Logger LOGGER = LogManager.getLogger(CommentService.class);


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
