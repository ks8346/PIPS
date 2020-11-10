package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.Upvotes;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProposalServiceTest {

    @MockBean
    ProposalDAO dao;

    @MockBean
    TeamDAO teamDAO;

    @MockBean
    CommentDAO commentDAO;

    @MockBean
    UpvotesDAO upvotesDAO;

    @Autowired
    ProposalService service;


    //Correct Values Testing
    @Test
    void getDefault() {

        try {
            LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
            Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            int page = 0, size = 5;
            Long teamId = 1L;

            Team team=new Team();
            team.setId(teamId);
            team.setName("hello");

            Proposal proposal1 = new Proposal();
            proposal1.setId(1L);
            Proposal proposal2 = new Proposal();
            proposal2.setId(2L);
            List<Proposal> proposals = new ArrayList<>();
            proposals.add(proposal1);
            proposals.add(proposal2);

            when(teamDAO.getTeam(teamId)).thenReturn(team);
            when(dao.getDefault(team,startDate,endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"))).thenReturn(proposals);

            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
            JSONObject body=new JSONObject();
            body.put("startDate",sdf.format(startDate));
            body.put("endDate",sdf.format(endDate));
            body.put("page",page);
            body.put("size",size);
            body.put("teamId",teamId);
            List<Proposal> proposalList=service.getDefault(body.toString());

            assertEquals(2,proposalList.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Reverse Dates Testing
    @Test
    void getDefaultWithReverseDates() {

        try {
            LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
            Date endDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date startDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            int page = 0, size = 5;
            Long teamId = 1L;

            Team team=new Team();
            team.setId(teamId);
            team.setName("hello");

            Proposal proposal1 = new Proposal();
            proposal1.setId(1L);
            Proposal proposal2 = new Proposal();
            proposal2.setId(2L);
            List<Proposal> proposals = new ArrayList<>();
            proposals.add(proposal1);
            proposals.add(proposal2);

            when(teamDAO.getTeam(teamId)).thenReturn(team);
            when(dao.getDefault(team,startDate,endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"))).thenReturn(proposals);

            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
            JSONObject body=new JSONObject();
            body.put("startDate",sdf.format(startDate));
            body.put("endDate",sdf.format(endDate));
            body.put("page",page);
            body.put("size",size);
            body.put("teamId",teamId);
            List<Proposal> proposalList=service.getDefault(body.toString());

            assertNull(proposalList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Throwing error from DAO Testing
    @Test
    void getDefaultWithDAOError() {

        try {
            LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
            Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            int page = 0, size = 5;
            Long teamId = 1L;

            Team team=new Team();
            team.setId(teamId);
            team.setName("hello");

            when(teamDAO.getTeam(teamId)).thenReturn(team);
            when(dao.getDefault(team,startDate,endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"))).thenThrow(new RuntimeException());

            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
            JSONObject body=new JSONObject();
            body.put("startDate",sdf.format(startDate));
            body.put("endDate",sdf.format(endDate));
            body.put("page",page);
            body.put("size",size);
            body.put("teamId",teamId);
            List<Proposal> proposalList=service.getDefault(body.toString());

            assertNull(proposalList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //Empty ArrayList Testing
    @Test
    void getDefaultEmptyArraylist() {

        try {
            LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
            Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
            int page = 0, size = 5;
            Long teamId = 1L;

            Team team=new Team();
            team.setId(teamId);
            team.setName("hello");


            List<Proposal> proposals = new ArrayList<>();

            when(teamDAO.getTeam(teamId)).thenReturn(team);
            when(dao.getDefault(team,startDate,endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"))).thenReturn(proposals);

            String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));
            JSONObject body=new JSONObject();
            body.put("startDate",sdf.format(startDate));
            body.put("endDate",sdf.format(endDate));
            body.put("page",page);
            body.put("size",size);
            body.put("teamId",teamId);
            List<Proposal> proposalList=service.getDefault(body.toString());

            assertNull(proposalList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //Correct Values Testing
    @Test
    void deleteProposal() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",1L);

            List<Comment> comments=new ArrayList<>();
            Comment comment=new Comment();
            comment.setId(1L);
            comments.add(comment);
            comment=new Comment();
            comment.setId(2L);
            comments.add(comment);
            when(commentDAO.fetchAllComments(anyLong())).thenReturn(comments);

            List<Upvotes> upvotes=new ArrayList<>();
            upvotes.add(new Upvotes());
            upvotes.add(new Upvotes());
            when(upvotesDAO.fetchAllUpvotes(anyLong())).thenReturn(upvotes);

            assertTrue(service.deleteProposal(jsonObject.toString()));
            verify(commentDAO,times(1)).fetchAllComments(anyLong());
            verify(commentDAO,atLeastOnce()).deleteComment(anyLong());
            verify(upvotesDAO,times(1)).fetchAllUpvotes(anyLong());
            verify(upvotesDAO,atLeastOnce()).deleteUpvote(any());
            verify(dao,times(1)).deleteProposal(anyLong());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    //Testing after error
    @Test
    void deleteProposalAfterException() {
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",1L);

            List<Comment> comments=new ArrayList<>();
            Comment comment=new Comment();
            comment.setId(1L);
            comments.add(comment);
            comment=new Comment();
            comment.setId(2L);
            comments.add(comment);

            when(commentDAO.fetchAllComments(anyLong())).thenReturn(comments);
            when(upvotesDAO.fetchAllUpvotes(anyLong())).thenThrow(new RuntimeException());

            assertFalse(service.deleteProposal(jsonObject.toString()));
            verify(commentDAO,times(1)).fetchAllComments(anyLong());
            verify(commentDAO,atLeastOnce()).deleteComment(anyLong());
            verify(upvotesDAO,times(1)).fetchAllUpvotes(anyLong());
            verify(upvotesDAO,never()).deleteUpvote(any());
            verify(dao,times(0)).deleteProposal(anyLong());
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }
    //Correct Values Testing
    @Test
    void saveProposal() throws Exception {
        Proposal p =new Proposal();
        p.setTitle("Hello");
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

        when(dao.saveProposal(any(Proposal.class))).thenReturn(p);

        Proposal proposal=service.saveProposal(object.toString());
        assertEquals(p.getTitle(),proposal.getTitle());
        verify(teamDAO,atLeastOnce()).getTeam(anyLong());
    }

    //Incorrect Values testing
    @Test
    void saveProposalError() throws Exception {
        Proposal p =new Proposal();
        p.setTitle("Hello");
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

        when(teamDAO.getTeam(anyLong())).thenThrow(new RuntimeException());

        assertThrows(Exception.class,()->service.saveProposal(object.toString()));
        verify(dao,never()).saveProposal(any(Proposal.class));
    }

    // Correct Values Testing
    @Test
    void updateProposal() throws Exception{
        Proposal p =new Proposal();
        p.setTitle("Hello");
        JSONObject object=new JSONObject();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");

        when(dao.getById(anyLong())).thenReturn(new Proposal());
        when(dao.saveProposal(any(Proposal.class))).thenReturn(p);

        Proposal proposal=service.updateProposal(object.toString());
        assertEquals(p.getTitle(),proposal.getTitle());
    }

    // Incorrect Values Testing
    @Test
    void updateProposalError() throws Exception{
        Proposal p =new Proposal();
        p.setTitle("Hello");
        JSONObject object=new JSONObject();
        object.put("key",2L);
        object.put("title","ABCD");
        object.put("description","WXYZ");

        when(dao.getById(anyLong())).thenThrow(new RuntimeException());

        assertThrows(Exception.class,()->service.updateProposal(object.toString()));
        verify(dao,never()).saveProposal(any(Proposal.class));
    }
}
