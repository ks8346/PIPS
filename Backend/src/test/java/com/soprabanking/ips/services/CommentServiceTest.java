package com.soprabanking.ips.services;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

	@Mock
	private CommentDAO commentDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private ProposalDAO proposalDAO;
	
	@InjectMocks
	CommentService commentService;
	
	
	
	@Test
	public void testDeleteComment() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);

		doNothing().when(commentDAO).deleteComment(anyLong());
		commentService.deleteComment(json.toString());
		verify(commentDAO,times(1)).deleteComment(anyLong());
	}
	
	@Test
	public void testDeleteCommentException() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);

		doThrow(new RuntimeException()).when(commentDAO).deleteComment(anyLong());
		assertThrows(Exception.class, ()->commentService.deleteComment(json.toString()));
	}
	
	@Test
	void testDisplayComments() throws Exception
	{
		Long proposalID=2L;
		
		Team t1=new Team();
		t1.setId(1L);
		t1.setName("DEVS");
		
		Team t2=new Team();
		t2.setId(2L);
		t2.setName("SPARKS");
		
		Set<Team> l1=new HashSet<Team>();
		
		
		User u1=new User();
		u1.setId(1L);
		u1.setName("Akanksha");
		u1.setEmail("ak@gmail.com");
		u1.setCreationDate(new Date());
		u1.setPassword("Pass*123@");
		u1.setRole("ROLE_USER");
		u1.setTeam(t1);
		
		Proposal p1 = new Proposal();
		p1.setId(proposalID);
        p1.setTitle("Title 1");
        p1.setDescription("Description 1");
        p1.setCreationDate(new Date());
        p1.setUser(u1);
        p1.setTeams(l1);
        
        Comment c1=new Comment();
        c1.setId(1L);
        Comment c2=new Comment();
        c2.setId(2L);
        Comment c3=new Comment();
        c3.setId(3L);
        List<Comment> comments=new ArrayList<>();
        comments.add(c1);
        comments.add(c2);
        comments.add(c3);
        
        when(commentDAO.fetchAllComments(proposalID)).thenReturn(comments);
        
        JSONObject body=new JSONObject();
        body.put("id", proposalID);
        
        List<Comment> resultList=commentService.displayComments(body.toString());
        
        assertEquals(3,resultList.size());
        verify(commentDAO,times(1)).fetchAllComments(anyLong());
        }
	
	@Test
	void testDisplayCommentsEmptyList() throws Exception
	{
		Long proposalID=6L;
		
		List<Comment> comments=new ArrayList<>();
		
		Team t1=new Team();
		t1.setId(1L);
		t1.setName("DEVS");
		
		Team t2=new Team();
		t2.setId(2L);
		t2.setName("SPARKS");
		
		Set<Team> l1=new HashSet<Team>();
		
		
		User u1=new User();
		u1.setId(1L);
		u1.setName("Akanksha");
		u1.setEmail("ak@gmail.com");
		u1.setCreationDate(new Date());
		u1.setPassword("Pass*123@");
		u1.setRole("ROLE_USER");
		u1.setTeam(t1);
		
		Proposal p1 = new Proposal();
		p1.setId(proposalID);
        p1.setTitle("Title 1");
        p1.setDescription("Description 1");
        p1.setCreationDate(new Date());
        p1.setUser(u1);
        p1.setTeams(l1);
        
        when(commentDAO.fetchAllComments(proposalID)).thenReturn(comments);
        
        JSONObject body=new JSONObject();
        body.put("id", proposalID);
        
        List<Comment> resultList=commentService.displayComments(body.toString());
        
        assertNull(resultList);
		
	}
	
	@Test
	void testDisplayCommentsAfterException() throws Exception
	{
		Long proposalID=1L;
		
		JSONObject body=new JSONObject();
        body.put("id", proposalID);
		
        when(commentDAO.fetchAllComments(proposalID)).thenThrow(new RuntimeException());
        
        assertThrows(Exception.class, ()->commentService.displayComments(body.toString()));
        verify(commentDAO,times(1)).fetchAllComments(anyLong());
		
	}
	
	@Test
	void testAddComment() throws Exception
	{
			Long userID=1L;
			Long proposalID=1L;
			String text="This is a comment";
			
			User u1=new User();
			u1.setId(userID);
			Proposal p1=new Proposal();
			p1.setId(proposalID);
			
			Comment comment=new Comment();
			comment.setId(1L);
			comment.setUser(u1);
			comment.setProposal(p1);
			comment.setCreationDate(new Date());
			comment.setComment(text);
			
	        when(userDAO.getById(userID)).thenReturn(u1);
	        when(proposalDAO.getById(proposalID)).thenReturn(p1);
	        when(commentDAO.createComment(any(Comment.class))).thenReturn(comment);
			
	        JSONObject body=new JSONObject();
	        body.put("text",text);
	        body.put("userId",userID);
	        body.put("id",proposalID);
	        
			Comment com=commentService.addComment(body.toString());
			
			assertEquals(comment.getId(),com.getId());
			assertNotNull(com);
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,times(1)).getById(anyLong());
			verify(commentDAO,times(1)).createComment(any(Comment.class));
	}
	
	@Test
	void testAddCommentDAOError() throws Exception
	{
			JSONObject body=new JSONObject();	
			body.put("text", "This is comment");
			body.put("userId", 3L);
			body.put("id",2L);
			User u1=new User();
			u1.setId(3L);
			Proposal u2=new Proposal();
			u2.setId(2L);
			
			when(userDAO.getById(anyLong())).thenReturn(u1);
			when(proposalDAO.getById(anyLong())).thenReturn(u2);
			when(commentDAO.createComment(any(Comment.class))).thenThrow(new RuntimeException());
			
			assertThrows(Exception.class,()->commentService.addComment(body.toString()));
			
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,times(1)).getById(anyLong());
			verify(commentDAO,times(1)).createComment(any(Comment.class));		
	}
	
	@Test
	void testAddCommentInvalidException() throws Exception
	{
			JSONObject body=new JSONObject();	
			body.put("text", "This is comment");
			body.put("userId", 3L);
			body.put("id",2L);
			
			when(userDAO.getById(anyLong())).thenReturn(null);
			
			assertThrows(Exception.class,()->commentService.addComment(body.toString()));
			
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,never()).getById(anyLong());
			verify(commentDAO,never()).createComment(any(Comment.class));		
	}
	
	
}
