package com.soprabanking.ips.daos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.CommentRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentDAOTest {
	
	@Mock
	private CommentRepository commentRepository;
	
	@InjectMocks
	private CommentDAO commentDAO;
	
	@Test
	public void testDeleteComment() {
		
		doNothing().when(commentRepository).deleteById(anyLong());
		
		commentDAO.deleteComment(anyLong());
		verify(commentRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	public void testDeleteCommentError() {
		
		doThrow(new RuntimeException()).when(commentRepository).deleteById(anyLong());
		
		assertThrows(Exception.class, () -> commentDAO.deleteComment(anyLong()));
		
	}
	
	@Test
	public void testFetchAllComments()
	{
		Long proposalId=1L;
		List<Comment> comments=new ArrayList<>();
		Comment c1=new Comment();
		Comment c2=new Comment();
		Comment c3=new Comment();
		comments.add(c1);
		comments.add(c2);
		comments.add(c3);
		
		when(commentRepository.findAllByProposalIdOrderByCreationDateDesc(anyLong())).thenReturn(comments);
		
		List<Comment> resultList=commentDAO.fetchAllComments(proposalId);
		
		assertEquals(3, resultList.size());
        assertNotNull(resultList);
        verify(commentRepository,times(1)).findAllByProposalIdOrderByCreationDateDesc(anyLong());
	}
	
	@Test
	public void testFetchAllCommentsIncorrectValues()
	{
		Long proposalId=-1L;
		when(commentRepository.findAllByProposalIdOrderByCreationDateDesc(anyLong())).thenThrow(new RuntimeException());
		
		assertThrows(Exception.class,()->commentDAO.fetchAllComments(proposalId));
		
	}
	
	@Test
	public void testCreateComment()
	{
		User u1=new User();
		u1.setId(1L);
		Proposal p1=new Proposal();
		p1.setId(1L);
		
		Comment comment=new Comment();
		comment.setId(1L);
		comment.setUser(u1);
		comment.setProposal(p1);
		comment.setComment("Comment");
		
		when(commentRepository.save(any(Comment.class))).thenReturn(comment);
		
		Comment com=commentDAO.createComment(comment);
		
		assertNotNull(com);
		assertEquals(com.getId(),comment.getId());
		assertEquals(com.getUser(),comment.getUser());
		assertEquals(com.getProposal(),comment.getProposal());
		assertEquals(com.getComment(),comment.getComment());
		
		verify(commentRepository,times(1)).save(any(Comment.class));
	}
	
	@Test
	public void testCreateCommentIncorrectValues()
	{
		User u1=new User();
		u1.setId(-1L);
		Proposal p1=new Proposal();
		p1.setId(-1L);
		Comment comment=new Comment();
		comment.setId(1L);
		comment.setUser(u1);
		comment.setProposal(p1);
		comment.setComment("Comment");
		
		when(commentRepository.save(any(Comment.class))).thenThrow(new RuntimeException());
		
		assertThrows(Exception.class,()->commentDAO.createComment(comment));
		
	}

}
