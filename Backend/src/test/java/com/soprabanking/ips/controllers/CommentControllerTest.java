package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.soprabanking.ips.services.CommentService;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CommentService commentService;
	
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
	
}
