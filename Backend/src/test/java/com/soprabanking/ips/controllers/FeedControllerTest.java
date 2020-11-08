package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.services.FeedService;
import com.soprabanking.ips.services.FeedServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    private FeedServiceTest feedServiceTest = new FeedServiceTest();

    @Mock
    private FeedService feedService;

    @InjectMocks
    private FeedController feedController;

   
    @Test
    public void testGetAllProposalFeed() throws Exception {

        Proposal proposal1 = new Proposal();
        Proposal proposal2 = new Proposal();

        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);
        String body = feedServiceTest.createAllFeedParams(new Date().toString(), "all");
        
        when(feedService.fetchAllProposals(body)).thenReturn(proposals);

        MvcResult actualResult = mockMvc.perform(post("/feed/all")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(actualResult.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(proposals));
    }
}
