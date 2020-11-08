package com.soprabanking.ips.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.repositories.TeamRepository;
import com.soprabanking.ips.repositories.UserRepository;
import com.soprabanking.ips.services.FeedService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FeedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private FeedService feedService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private FeedController feedController;

    @Test
    public void testSave() throws Exception {
		
		/*Date date = new Date();
		Team team = new Team();
		team.setName("1");
		
		User user = new User();
		user.setEmail("sa");
		user.setTeam(team);
		
		when(teamRepository.save(team)).thenReturn(team);
		when(userRepository.save(user)).thenReturn(user);
		
		//user.setId(1);;
		team.setId((long) 1);
		user.setTeam(team);
		when(userRepository.getOne((long) 1)).thenReturn(user);
		
		MvcResult result =  mockMvc.perform(get("/feed/save")
				.content(objectMapper.writeValueAsString(user)))
			.andExpect(status().isOk())
			.andReturn();*/

        //assertThat(result.getResponse().getContentAsString())
        //.isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(user));
    }

    /////Error with parameters
    @Test
    public void testGetAllProposalFeed() throws Exception {


        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        //Date date = new SimpleDateFormat(dateFromat).parse("2020-10-29T16:08:59.962+05:30");

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("IST"));

        String ds = sdf.format(now);

        Proposal proposal1 = new Proposal();
        Proposal proposal2 = new Proposal();

        System.out.println(now);
        System.out.println(ds);

        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);
		
		/*when(feedService.fetchAllProposals(now, now, 0, 5))
		.thenReturn(proposals);*/

        MvcResult actualResult = mockMvc.perform(post("/feed/all")
                .param("startDate", ds)
                .param("endDate", ds)
                .param("page", "0")
                .param("size", "5"))
                .andExpect(status().is4xxClientError())
                .andReturn();

        System.out.println(actualResult.getResponse().getContentAsString());
        //assertThat(actualResult.getResponse().getContentAsString())
        //.isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(proposals));
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

        MvcResult mvcResult = mockMvc.perform(post("/feed/team")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body.toString()))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        List<Proposal> proposals= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Proposal[].class));
        assertNotNull(proposals);

    }

    // Team containing no feed
    @Test
    void getTeamProposalNoFeed() throws Exception {
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int page = 0, size = 5;
        Long teamId = 1L;

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
        JSONObject body=new JSONObject();
        body.put("startDate",sdf.format(startDate));
        body.put("endDate",sdf.format(endDate));
        body.put("page",page);
        body.put("size",size);
        body.put("teamId",teamId);

        MvcResult mvcResult = mockMvc.perform(post("/feed/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body.toString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        List<Proposal> proposals= Arrays.asList(objectMapper.readValue(mvcResult.getResponse().getContentAsString(),Proposal[].class));
        assertTrue(proposals.isEmpty());

    }
}
