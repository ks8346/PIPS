package com.soprabanking.ips.controllers;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.services.ProposalService;

@SpringBootTest
@AutoConfigureMockMvc
class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProposalService proposalService;
    
    @Test
    public void testShareProposal() throws Exception {
    	
    	String body = shareProposalData();
    	
    	when(proposalService.shareProposal(body)).thenReturn(new Proposal());
    	
    	MvcResult mvcResult = mockMvc.perform(post("/proposal/share")
    	        .contentType(MediaType.APPLICATION_JSON)
    	        .content(body))
    	        .andExpect(status().isOk())
    	        .andReturn();
    	
    	assertEquals("SUCCESS", mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    public void testShareProposalError() throws Exception {
    	
    	String body = shareProposalData();
    	
    	when(proposalService.shareProposal(body)).thenThrow(new RuntimeException());
    	
    	MvcResult mvcResult = mockMvc.perform(post("/proposal/share")
    	        .contentType(MediaType.APPLICATION_JSON)
    	        .content(body))
    	        .andExpect(status().is4xxClientError())
    	        .andReturn();
    	
    	assertEquals("FAILURE", mvcResult.getResponse().getContentAsString());
    }


    //Correct Values Testing
    @Test
    void deleteProposal() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",5L);

        //Mockito.when(proposalDAO.getById(ArgumentMatchers.anyLong())).thenReturn(new Proposal());
        //Mockito.when(feedService.fetchAllProposals(ArgumentMatchers.anyString())).thenReturn(new ArrayList<>());

        Mockito.when(proposalService.deleteProposal(ArgumentMatchers.anyString())).thenReturn(true);

        MvcResult mvcResult = mockMvc.perform(post("/proposal/delete")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("SUCCESS",mvcResult.getResponse().getContentAsString());
    }

    //Incorrect Values Testing
    @Test
    void deleteProposalIncorrect() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1L);

        Mockito.when(proposalService.deleteProposal(ArgumentMatchers.anyString())).thenReturn(false);

        MvcResult mvcResult = mockMvc.perform(post("/proposal/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals("FAILURE",mvcResult.getResponse().getContentAsString());
    }
    
    @Test
    void deleteProposalError() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",1L);

        Mockito.when(proposalService.deleteProposal(ArgumentMatchers.anyString())).thenThrow(new RuntimeException());

        MvcResult mvcResult = mockMvc.perform(post("/proposal/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals("FAILURE",mvcResult.getResponse().getContentAsString());
    }

    //Correct Values
    @Test
    void addProposal() throws Exception {
        JSONObject object=new JSONObject();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");
        object.put("userId",1L);

        Team team=new Team();
        team.setId(1L);
        team.setName("Random");
        JSONArray jsonArray=new JSONArray();
        ObjectMapper objectMapper=new ObjectMapper();
        jsonArray.put(new JSONObject(objectMapper.writeValueAsString(team)));
        object.put("teams",jsonArray);

        Proposal p =new Proposal();
        p.setTitle("Hello");

        Mockito.when(proposalService.saveProposal(ArgumentMatchers.anyString())).thenReturn(p);

        MvcResult mvcResult = mockMvc.perform(post("/proposal/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(p));
    }

    //Incorrect Values testing
    @Test
    void addProposalError() throws Exception {
        JSONObject object=new JSONObject();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");
        object.put("userId",1L);

        Team team=new Team();
        team.setId(1L);
        team.setName("Random");
        JSONArray jsonArray=new JSONArray();
        ObjectMapper objectMapper=new ObjectMapper();
        jsonArray.put(new JSONObject(objectMapper.writeValueAsString(team)));
        object.put("teams",jsonArray);

        Proposal p =new Proposal();
        p.setTitle("Hello");

        Mockito.when(proposalService.saveProposal(ArgumentMatchers.anyString())).thenThrow(new Exception());

        MvcResult mvcResult = mockMvc.perform(post("/proposal/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(new Proposal()));
    }

    //Correct Values Testing
    @Test
    void updateProposal() throws Exception {
        JSONObject object=new JSONObject();
        ObjectMapper objectMapper=new ObjectMapper();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");

        Proposal p =new Proposal();
        p.setTitle("Hello");

        Mockito.when(proposalService.updateProposal(ArgumentMatchers.anyString())).thenReturn(p);

        MvcResult mvcResult = mockMvc.perform(post("/proposal/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(p));
    }

    //Incorrect Values testing
    @Test
    void updateProposalError() throws Exception {
        JSONObject object=new JSONObject();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");
        ObjectMapper objectMapper=new ObjectMapper();

        Proposal p =new Proposal();
        p.setTitle("Hello");

        Mockito.when(proposalService.updateProposal(ArgumentMatchers.anyString())).thenThrow(new Exception());

        MvcResult mvcResult = mockMvc.perform(post("/proposal/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(object.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(new Proposal()));
    }
    
    private String shareProposalData() throws JSONException {
    	
    	JSONObject json = new JSONObject();
    	json.put("id", 1);
    	JSONArray teams = new JSONArray();
		JSONObject t = new JSONObject();
		t.put("id","1");
    	t.put("name","Devs");
        teams.put(t);
        JSONObject t2 = new JSONObject();
		t.put("id","2");
    	t.put("name","Sparks");
    	teams.put(t2);
    	json.put("teams", teams);
    	
    	return json.toString();
    	
    	
    }
}
