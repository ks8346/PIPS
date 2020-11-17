package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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


import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.services.UpvotesService;

@SpringBootTest
@AutoConfigureMockMvc
public class UpvotesControllerTest 
{

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UpvotesService upvoteService;
	
	@Test
	public void testUpvoteProposal() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		
		Upvotes upvote=new Upvotes();
		upvote.setId(1L);
		
		when((upvoteService).upvoteProposal(json.toString())).thenReturn(upvote);
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/like")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("SUCCESS");
	}
	
	@Test
	public void testUpvoteProposalException() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		
		
		when((upvoteService).upvoteProposal(json.toString())).thenThrow(new RuntimeException());
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/like")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("FAILURE");
	}
	
	
	@Test
	public void testReverseUpvoteProposal() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		
		Upvotes upvote=new Upvotes();
		upvote.setId(1L);
		
		doNothing().when(upvoteService).reverseUpvote(json.toString());
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/dislike")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("SUCCESS");
	}
	
	@Test
	public void testReverseUpvoteProposalException() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 2L);
		
		doThrow(new RuntimeException()).when(upvoteService).reverseUpvote(json.toString());
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/dislike")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("FAILURE");
	}
	@Test
	public void testHasUpvotedTrue() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		when(upvoteService.hasUpvoted(json.toString())).thenReturn(true);
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/hasUpvoted")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("true");
		
	}
	
	@Test
	public void testHasUpvotedFalse() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		when(upvoteService.hasUpvoted(json.toString())).thenReturn(false);
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/hasUpvoted")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().isOk())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("false");
		
	}
	
	@Test
	public void testHasUpvotedException() throws Exception
	{
		JSONObject json = new JSONObject();
		json.put("id", 1L);
		json.put("userId", 3L);
		
		when(upvoteService.hasUpvoted(json.toString())).thenThrow(new RuntimeException());
		
		MvcResult actualResult = mockMvc.perform(post("/upvotes/hasUpvoted")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(json.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
		
		assertThat(actualResult.getResponse().getContentAsString())
			.isEqualTo("false");
		
	}
}
