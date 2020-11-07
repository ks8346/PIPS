package com.soprabanking.ips.controllers;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProposalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //Correct Values Testing
    @Test
    void deleteProposal() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",2L);

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

        MvcResult mvcResult = mockMvc.perform(post("/proposal/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals("FAILURE",mvcResult.getResponse().getContentAsString());
    }
}
