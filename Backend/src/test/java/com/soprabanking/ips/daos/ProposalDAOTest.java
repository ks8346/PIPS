package com.soprabanking.ips.daos;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.repositories.ProposalRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProposalDAOTest {

    @MockBean
    ProposalRepository proposalRepository;

    @Autowired
    ProposalDAO proposalDAO;

    //Correct Values Testing
    @Test
    void getDefault() {
        Team team=new Team();
        team.setId(1L);
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int page = 0, size = 5;
        Pageable pageable= PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount");

        Proposal proposal1 = new Proposal();
        proposal1.setId(1L);
        Proposal proposal2 = new Proposal();
        proposal2.setId(2L);
        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);

        when(proposalRepository.findByTeamsAndCreationDateBetween(team,startDate,endDate,pageable)).thenReturn(proposals);

        List<Proposal> proposalsList=proposalDAO.getDefault(team,startDate,endDate,pageable);
        assertNotNull(proposalsList);
        assertEquals(2,proposalsList.size());

    }

    //Incorrect Values Testing
    @Test
    void getDefaultOnIncorrectValues() {
        Team team=new Team();
        team.setId(-1L);
        LocalDate localDate = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDate();
        Date startDate = Date.from(localDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay(ZoneId.systemDefault()).toInstant());
        int page = 0, size = 5;
        Pageable pageable= PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount");

        when(proposalRepository.findByTeamsAndCreationDateBetween(team,startDate,endDate,pageable)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class,()->proposalDAO.getDefault(team,startDate,endDate,pageable));

    }

    //Correct Values Testing
    @Test
    void deleteProposal() {
        doNothing().when(proposalRepository).deleteById(anyLong());
        proposalDAO.deleteProposal(2L);
        verify(proposalRepository,times(1)).deleteById(anyLong());
    }

    //Incorrect Values Testing
    @Test
    void deleteProposalOnIncorrectValues() {
        doThrow(RuntimeException.class).when(proposalRepository).deleteById(anyLong());

        assertThrows(RuntimeException.class,()->proposalDAO.deleteProposal(-1L));
    }

    //Correct Values Testing
    @Test
    void getById() {
        Proposal p =new Proposal();
        p.setTitle("Hello");

        when(proposalRepository.getOne(anyLong())).thenReturn(p);

        Proposal proposal=proposalDAO.getById(1L);
        assertEquals(p.getTitle(),proposal.getTitle());
    }

    //Correct Values Testing
    @Test
    void saveProposal() {
        Proposal p =new Proposal();
        p.setTitle("Hello");

        when(proposalRepository.save(any(Proposal.class))).thenReturn(p);

        Proposal proposal=proposalDAO.saveProposal(p);
        assertEquals(p.getTitle(),proposal.getTitle());
    }
}
