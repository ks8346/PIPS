package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.models.Proposal;

@SpringBootTest
public class FeedServiceTest {

    @Mock
    ProposalDAO proposalDAO;

    @InjectMocks
    FeedService feedService;

    @Test
    public void testFetchAllProposals() {

        Proposal proposal1 = new Proposal();
        Proposal proposal2 = new Proposal();

        List<Proposal> proposals = new ArrayList<>();
        proposals.add(proposal1);
        proposals.add(proposal2);

        Pageable pageable = PageRequest.of(0, 5);
        Date date = new Date();
        Slice<Proposal> slice = new SliceImpl<>(proposals);

        when(proposalDAO.fetchAllProposals(date, date, pageable))
                .thenReturn(slice);

        /*assertEquals(proposals, feedService.fetchAllProposals(date, date, 0, 5));*/
    }


}
