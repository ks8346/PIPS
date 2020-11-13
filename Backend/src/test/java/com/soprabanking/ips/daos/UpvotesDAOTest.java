package com.soprabanking.ips.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UpvotesRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpvotesDAOTest 
{

	@Mock
	private UpvotesRepository upvoteRepository;
	
	@InjectMocks
	private UpvotesDAO upvoteDAO;
	
	@Test
	void testCreateUpvote()
	{
		User u1=new User();
		u1.setId(1L);
		Proposal p1=new Proposal();
		p1.setId(1L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		when(upvoteRepository.save(any(Upvotes.class))).thenReturn(up);
		
		Upvotes upvote=upvoteDAO.createUpvote(up);
		
		assertNotNull(upvote);
		assertEquals(upvote.getId(),up.getId());
		assertEquals(upvote.getUser(),up.getUser());
		assertEquals(upvote.getProposal(),up.getProposal());
		
		verify(upvoteRepository,times(1)).save(any(Upvotes.class));
	}
	
	@Test
	void testCreateUpvoteIncorrectValues()
	{
		User u1=new User();
		u1.setId(-1L);
		Proposal p1=new Proposal();
		p1.setId(-1L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		when(upvoteRepository.save(any(Upvotes.class))).thenThrow(new RuntimeException());
		
	    assertThrows(Exception.class,()->upvoteDAO.createUpvote(up));
	}
	
	@Test
	void testDeleteUpvote()
	{
		User u1=new User();
		u1.setId(1L);
		Proposal p1=new Proposal();
		p1.setId(2L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		doNothing().when(upvoteRepository).delete(any(Upvotes.class));
		
		upvoteDAO.deleteUpvote(up);
		
		verify(upvoteRepository,times(1)).delete(any(Upvotes.class));
	}
	
	@Test
	void testDeleteUpvoteIncorrectValues()
	{
		User u1=new User();
		u1.setId(4L);
		Proposal p1=new Proposal();
		p1.setId(5L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		doThrow(new RuntimeException()).when(upvoteRepository).delete(any(Upvotes.class));
		
		assertThrows(Exception.class,()->upvoteDAO.deleteUpvote(up));
	}
	
	@Test
	void testGetUpvoteForUserIdAndProposalId()
	{
		User u1=new User();
		u1.setId(4L);
		Proposal p1=new Proposal();
		p1.setId(5L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		when(upvoteRepository.findByUserIdAndProposalId(anyLong(),anyLong())).thenReturn(up);
		
		Upvotes upvote=upvoteDAO.getUpvoteforUserIdAndProposalId(4L,5L);
		
		assertNotNull(upvote);
		assertEquals(upvote.getId(),up.getId());
		assertEquals(upvote.getUser(),up.getUser());
		assertEquals(upvote.getProposal(),up.getProposal());
		
		verify(upvoteRepository,times(1)).findByUserIdAndProposalId(anyLong(), anyLong());
	}
	
	@Test
	void testGetUpvoteForUserIdAndProposalIdIncorrectValues()
	{
		User u1=new User();
		u1.setId(-4L);
		Proposal p1=new Proposal();
		p1.setId(-5L);
		
		Upvotes up=new Upvotes();
		up.setId(1L);
		up.setUser(u1);
		up.setProposal(p1);
		
		when(upvoteRepository.findByUserIdAndProposalId(anyLong(),anyLong())).thenThrow(new RuntimeException());
		
		
		assertThrows(Exception.class,()->upvoteDAO.getUpvoteforUserIdAndProposalId(4L,5L));
	}
	
	@Test
	public void testFetchAllUpvotes()
	{
		Long proposalId=1L;
		List<Upvotes> upvotes=new ArrayList<>();
		Upvotes u1=new Upvotes();
		Upvotes u2=new Upvotes();
		Upvotes u3=new Upvotes();
		upvotes.add(u1);
		upvotes.add(u2);
		upvotes.add(u3);
		
		when(upvoteRepository.findAllByProposalId(anyLong())).thenReturn(upvotes);
		
		List<Upvotes> resultList=upvoteDAO.fetchAllUpvotes(proposalId);
		
		assertEquals(3, resultList.size());
        assertNotNull(resultList);
        verify(upvoteRepository,times(1)).findAllByProposalId(anyLong());
	}
	
	@Test
	public void testFetchAllUpvotesIncorrectValues()
	{
		Long proposalId=-1L;
		when(upvoteRepository.findAllByProposalId(anyLong())).thenThrow(new RuntimeException());
		
		assertThrows(Exception.class,()->upvoteDAO.fetchAllUpvotes(proposalId));
		
	}
}
