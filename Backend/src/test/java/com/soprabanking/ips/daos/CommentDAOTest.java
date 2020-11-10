package com.soprabanking.ips.daos;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
