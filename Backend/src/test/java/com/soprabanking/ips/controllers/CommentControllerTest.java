package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.services.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CommentService commentService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
	public void testDeleteComment() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		
		doNothing().when(commentService).deleteComment(json.toString());
				
		MvcResult actualResult = mockMvc.perform(post("/comment/delete")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("SUCCESS");
	}
	
	@Test
	public void testDeleteCommentException() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		
		doThrow(new RuntimeException()).when(commentService).deleteComment(json.toString());
				
		MvcResult actualResult = mockMvc.perform(post("/comment/delete")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("FAILURE");
	}
	
	@Test
	public void testDisplayComments() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		
		Comment c1=new Comment();
		c1.setId(1L);
		Comment c2=new Comment();
		c2.setId(2L);
		List<Comment> comments=new ArrayList<>();
		comments.add(c1);
		comments.add(c2);
		when(commentService.displayComments(json.toString())).thenReturn(comments);
				
		MvcResult actualResult = mockMvc.perform(post("/comment/all")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(comments));
	}
	
	@Test
	public void testDisplayCommentsEmpty()throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		
		Comment c1=new Comment();
		c1.setId(1L);
		Comment c2=new Comment();
		c2.setId(2L);
		List<Comment> comments=new ArrayList<>();
		comments.add(c1);
		comments.add(c2);
		
		when(commentService.displayComments(json.toString())).thenThrow(new RuntimeException());
		
		MvcResult actualResult = mockMvc.perform(post("/comment/all")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		List<Comment> result= Arrays.asList(objectMapper.readValue(actualResult.getResponse().getContentAsString(),Comment[].class));
        assertTrue(result.isEmpty());
		
	}
	
	@Test
	public void testAddComment() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		json.put("text", "Hello this is comment");
		
		Comment comment=new Comment();
		comment.setId(1L);
		
		when((commentService).addComment(json.toString())).thenReturn(comment);
				
		MvcResult actualResult = mockMvc.perform(post("/comment/add")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("SUCCESS");
	}
	
	@Test
	public void testAddCommentException() throws Exception {
		
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		json.put("text", "Hello this is comment");
		
		Comment comment=new Comment();
		comment.setId(1L);
		
		when((commentService).addComment(json.toString())).thenThrow(new RuntimeException());
				
		MvcResult actualResult = mockMvc.perform(post("/comment/add")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("FAILURE");
	}
	
	
	
}
