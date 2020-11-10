package com.soprabanking.ips.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.JSONObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.models.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpvotesServiceTest 
{
	@Mock
	private UpvotesDAO upvotesDAO;
	
	@Mock
	private UserDAO userDAO;
	
	@Mock
	private ProposalDAO proposalDAO;
	
	@InjectMocks
	UpvotesService upvoteService;
	
	@Test
	public void testUpvoteProposalNew() throws Exception
	{
		Long proposalID=1L;
		Long userID=1L;
		
		User user=new User();
		user.setId(userID);
		Proposal proposal=new Proposal();
		proposal.setId(proposalID);
		proposal.setUpvotesCount(proposal.getUpvotesCount()+1);
		
		Upvotes upvote=new Upvotes();
		upvote.setId(1L);
		upvote.setProposal(proposal);
		upvote.setUser(user);
		
		when(userDAO.getById(anyLong())).thenReturn(user);
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(anyLong(), anyLong())).thenReturn(null);
		when(proposalDAO.getById(anyLong())).thenReturn(proposal);
		when(proposalDAO.saveProposal(any(Proposal.class))).thenReturn(proposal);
		when(upvotesDAO.createUpvote(any(Upvotes.class))).thenReturn(upvote);
		
		JSONObject body=new JSONObject();
        body.put("userId",userID);
        body.put("id",proposalID);
        
        Upvotes upvoted=upvoteService.upvoteProposal(body.toString());
        
        assertEquals(upvoted.getId(),upvote.getId());
		assertNotNull(upvoted);
		verify(userDAO,times(1)).getById(anyLong());
		verify(proposalDAO,times(1)).getById(anyLong());
		verify(proposalDAO,times(1)).saveProposal(any(Proposal.class));
		verify(upvotesDAO,times(1)).createUpvote(any(Upvotes.class));
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
	}
	
	@Test
	public void testUpvoteProposalExisting() throws Exception
	{
		Long proposalID=1L;
		Long userID=1L;
		
		User user=new User();
		user.setId(userID);
		Proposal proposal=new Proposal();
		proposal.setId(proposalID);
		
		Upvotes upvote=new Upvotes();
		upvote.setId(1L);
		upvote.setProposal(proposal);
		upvote.setUser(user);
		
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(anyLong(), anyLong())).thenReturn(upvote);
		
		JSONObject body=new JSONObject();
        body.put("userId",userID);
        body.put("id",proposalID);
        
        Upvotes upvoted=upvoteService.upvoteProposal(body.toString());
        
        assertEquals(upvoted.getId(),upvote.getId());
		assertNotNull(upvoted);
		verify(userDAO,never()).getById(anyLong());
		verify(proposalDAO,never()).getById(anyLong());
		verify(proposalDAO,never()).saveProposal(any(Proposal.class));
		verify(upvotesDAO,never()).createUpvote(any(Upvotes.class));
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
	}
	
	
	@Test
	void testUpvoteProposalDAOError() throws Exception
	{
			JSONObject body=new JSONObject();	
			body.put("userId", 3L);
			body.put("id",2L);
			User u1=new User();
			u1.setId(3L);
			Proposal u2=new Proposal();
			u2.setId(2L);
			
			when(userDAO.getById(anyLong())).thenReturn(u1);
			when(proposalDAO.getById(anyLong())).thenReturn(u2);
			when(upvotesDAO.createUpvote(any(Upvotes.class))).thenThrow(new RuntimeException());
			
			assertThrows(Exception.class,()->upvoteService.upvoteProposal(body.toString()));
			
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,times(1)).getById(anyLong());
			verify(upvotesDAO,times(1)).createUpvote(any(Upvotes.class));		
	}
	
	@Test
	void testUpvoteProposalInvalidException() throws Exception
	{
			JSONObject body=new JSONObject();	
			body.put("userId", 3L);
			body.put("id",2L);
			
			when(userDAO.getById(anyLong())).thenReturn(null);
			
			assertThrows(Exception.class,()->upvoteService.upvoteProposal(body.toString()));
			
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,never()).getById(anyLong());
			verify(upvotesDAO,never()).createUpvote(any(Upvotes.class));		
	}
	
	@Test
	public void testReverseUpvote() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		User user=new User();
		user.setId(3L);
		Proposal proposal=new Proposal();
		proposal.setId(1L);
		proposal.setUpvotesCount(proposal.getUpvotesCount()-1);
		Upvotes upvote=new Upvotes();
		upvote.setUser(user);
		upvote.setProposal(proposal);
		
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(anyLong(), anyLong())).thenReturn(upvote);
		when(proposalDAO.getById(anyLong())).thenReturn(proposal);
        when(proposalDAO.saveProposal(any(Proposal.class))).thenReturn(proposal);
        
		doNothing().when(upvotesDAO).deleteUpvote(any(Upvotes.class));
		
		upvoteService.reverseUpvote(json.toString());
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
		verify(proposalDAO,times(1)).saveProposal(any(Proposal.class));
		verify(upvotesDAO,times(1)).deleteUpvote(any(Upvotes.class));
	}
	
	@Test
	public void testReverseUpvoteDAOError() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		User user=new User();
		user.setId(3L);
		Proposal proposal=new Proposal();
		proposal.setId(1L);
		proposal.setUpvotesCount(proposal.getUpvotesCount()-1);
		Upvotes upvote=new Upvotes();
		upvote.setUser(user);
		upvote.setProposal(proposal);
		
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(3L, 1L)).thenReturn(upvote);
		when(proposalDAO.getById(anyLong())).thenReturn(proposal);
        when(proposalDAO.saveProposal(any(Proposal.class))).thenReturn(proposal);
		doThrow(new RuntimeException()).when(upvotesDAO).deleteUpvote(any(Upvotes.class));
		
		assertThrows(Exception.class, ()->upvoteService.reverseUpvote(json.toString()));
		
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
		verify(proposalDAO,times(1)).getById(anyLong());
		verify(proposalDAO,times(1)).saveProposal(any(Proposal.class));
		verify(upvotesDAO,times(1)).deleteUpvote(any(Upvotes.class));
	}

	@Test
	void testReverseUpvoteInvalidException() throws Exception
	{
			JSONObject body=new JSONObject();	
			body.put("userId", 3L);
			body.put("id",2L);
			
			when(userDAO.getById(anyLong())).thenReturn(null);
			
			assertThrows(Exception.class,()->upvoteService.upvoteProposal(body.toString()));
			
			verify(userDAO,times(1)).getById(anyLong());
			verify(proposalDAO,never()).getById(anyLong());
			verify(upvotesDAO,never()).createUpvote(any(Upvotes.class));		
	}
	
	@Test
	void testHasUpvotedTrue() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		User user=new User();
		user.setId(3L);
		Proposal proposal=new Proposal();
		proposal.setId(1L);
		Upvotes upvote=new Upvotes();
		upvote.setUser(user);
		upvote.setProposal(proposal);
		
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(anyLong(),anyLong())).thenReturn(upvote);
		
		assertNotNull(upvote);
		assertTrue(upvoteService.hasUpvoted(json.toString()));
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
		
	}
	
	@Test
	void testHasUpvotedFalse() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		Upvotes upvote=null;
		
		when(upvotesDAO.getUpvoteforUserIdAndProposalId(anyLong(),anyLong())).thenReturn(upvote);
		
		assertNull(upvote);
		assertFalse(upvoteService.hasUpvoted(json.toString()));
		verify(upvotesDAO,times(1)).getUpvoteforUserIdAndProposalId(anyLong(), anyLong());
		
	}
}
