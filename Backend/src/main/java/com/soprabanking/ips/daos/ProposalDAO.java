package com.soprabanking.ips.daos;

import java.util.Date;
import java.util.List;

import com.soprabanking.ips.models.Team;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.repositories.ProposalRepository;
import com.soprabanking.ips.services.TeamService;

@Component
public class ProposalDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(ProposalDAO.class);

    @Autowired
    private ProposalRepository proposalRepository;

    public Slice<Proposal> fetchAllProposals(Date startDate, Date endDate, Pageable pageable) {

    	LOGGER.info("Inside ProposalDAO : fetchAllProposals() method");
        return proposalRepository.findAllByCreationDateBetweenOrderByUpvotesCountDesc(startDate, endDate, pageable);

    }


    public List<Proposal> getDefault(Team team, Date start, Date end, Pageable pageable) {
    	
    	LOGGER.info("Inside ProposalDAO : getDefault() method");
        return proposalRepository.findByTeamsAndCreationDateBetween(team, start, end, pageable);

    }

    public Slice<Proposal> fetchUserProposals(Long id, Date startDate, Date endDate, Pageable pageable) {

    	LOGGER.info("Inside ProposalDAO : fetchUserProposals() method");
        return proposalRepository.findAllByUserIdAndCreationDateBetweenOrderByUpvotesCountDesc(id, startDate, endDate, pageable);
    }

    public Proposal getById(Long id) {
    	
    	LOGGER.info("Inside ProposalDAO : getById() method");
        return proposalRepository.getOne(id);

    }

    public void deleteProposal(Long id) {
    	
    	LOGGER.info("Inside ProposalDAO : deleteProposal() method");
        proposalRepository.deleteById(id);
    }

    public Proposal saveProposal(Proposal p) {

    	LOGGER.info("Inside ProposalDAO : saveProposal() method");
        Proposal proposal = proposalRepository.save(p);
        return proposal;
    }

}
