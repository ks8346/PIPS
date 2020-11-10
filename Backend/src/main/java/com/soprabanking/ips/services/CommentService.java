package com.soprabanking.ips.services;

import java.util.Date;
import java.util.List;

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


    public List<Comment> displayComments(String body) throws Exception {
        try {
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long proposalId = Long.parseLong(jsonObj.get("id").asText());

            List<Comment> result = commentDao.fetchAllComments(proposalId);
            return !result.isEmpty()?result:null;
        } catch (Exception ex) {
            throw new Exception();
        }
    }

    public Comment addComment(String body) throws Exception {
        try {
            	JsonNode jsonObj = JsonUtil.stringToJson(body);
            	String text = jsonObj.get("text").asText();
            	Long uid = Long.parseLong(jsonObj.get("userId").asText());
            	Long pid = Long.parseLong(jsonObj.get("id").asText());
            	User user = userDao.getById(uid);
            	Comment comment = new Comment();
            	Comment addedComment;
            	if(user!=null)
            	{
            	Proposal proposal = proposalDao.getById(pid);
            	if(proposal!= null)
            	{
	            comment.setComment(text);
	           	comment.setUser(user);
	           	comment.setProposal(proposal);
	           	comment.setCreationDate(new Date());
	           	addedComment = commentDao.createComment(comment);
            	} 
            	else
            	{
            		throw new Exception();
            	}
            	}
            	else
            	{
            		throw new Exception();
            	}
	            
            	return addedComment;
            	
        	} 
        catch (Exception e) {
            throw new Exception();
        }


    }

    public void deleteComment(String body) throws Exception {

        try {
            JsonNode jsonObj = JsonUtil.stringToJson(body);

            Long id = Long.parseLong(jsonObj.get("id").asText());

            commentDao.deleteComment(id);

        } catch (Exception ex) {
            throw new Exception();
        }
    }


}
