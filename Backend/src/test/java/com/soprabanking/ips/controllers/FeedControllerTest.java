package com.soprabanking.ips.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.services.FeedService;
import com.soprabanking.ips.services.FeedServiceTest;
import com.soprabanking.ips.services.ProposalService;


@SpringBootTest
@AutoConfigureMockMvc
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    private FeedServiceTest feedServiceTest = new FeedServiceTest();

    @MockBean
    private FeedService feedService;

    @MockBean
    private ProposalService proposalService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @Autowired
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
    
    @Test
    public void testGetUserProposalFeed() throws Exception {

        Proposal proposal1 = new Proposal();
        Proposal proposal2 = new Proposal();

        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);

        
        String body = feedServiceTest.createAllFeedParams(new Date().toString(), "create");
        
        when(feedService.fetchUserProposals(body)).thenReturn(proposals);
        
        MvcResult actualResult = mockMvc.perform(post("/feed/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(actualResult.getResponse().getContentAsString())
        .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(proposals));

    }
    
    

    // Team containing feed
    @Test
    void getTeamProposalFeed() throws Exception {
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int page = 0, size = 5;
        Long teamId = 2L;

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
        JSONObject body=new JSONObject();
        body.put("startDate",sdf.format(startDate));
        body.put("endDate",sdf.format(endDate));
        body.put("page",page);
        body.put("size",size);
        body.put("teamId",teamId);

        Proposal proposal1 = new Proposal();
        Proposal proposal2 = new Proposal();

        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);

        Mockito.when(proposalService.getDefault(anyString()))
                .thenReturn(proposals);

        MvcResult mvcResult = mockMvc.perform(post("/feed/team")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body.toString()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString())
                .isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(proposals));

        /*List<Proposal> proposals= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Proposal[].class));
        assertNotNull(proposals);*/

    }

    // Team containing no feed
    @Test
    void getTeamProposalNoFeed() throws Exception {
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int page = 0, size = 5;
        Long teamId = 4L;

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
        JSONObject body=new JSONObject();
        body.put("startDate",sdf.format(startDate));
        body.put("endDate",sdf.format(endDate));
        body.put("page",page);
        body.put("size",size);
        body.put("teamId",teamId);

        Mockito.when(proposalService.getDefault(anyString()))
                .thenReturn(null);

        MvcResult mvcResult = mockMvc.perform(post("/feed/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        List<Proposal> proposals= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Proposal[].class));
        assertTrue(proposals.isEmpty());

    }
}
