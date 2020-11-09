package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.daos.CommentDAO;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

	@Mock
	private CommentDAO commentDAO;
	
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
	
}
