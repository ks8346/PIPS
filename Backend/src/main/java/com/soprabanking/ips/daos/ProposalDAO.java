package com.soprabanking.ips.daos;

import java.util.Date;
import java.util.List;

import com.soprabanking.ips.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.repositories.ProposalRepository;

@Component
public class ProposalDAO {

	@Autowired
	private ProposalRepository proposalRepository;
	
	public Slice<Proposal> fetchAllProposals(Date startDate, Date endDate, Pageable pageable) {
		
		return proposalRepository.findAllByCreationDateBetweenOrderByUpvotesCountDesc(startDate, endDate, pageable);
		
	}


	public List<Proposal> getDefault(Team team, Date start, Date end, Pageable pageable){
		return proposalRepository.findByTeamsAndCreationDateBetween(team,start,end,pageable);

	}
	
	public Slice<Proposal> fetchUserProposals(Long id, Date startDate, Date endDate, Pageable pageable){
		
		return proposalRepository.findAllByUserIdAndCreationDateBetweenOrderByUpvotesCountDesc(id, startDate, endDate, pageable);
	}

	public Proposal getById(Long id)
	{
		return proposalRepository.getOne(id);

	}

	public void deleteProposal(Long id){
		proposalRepository.deleteById(id);
	}
	
	public Proposal saveProposal(Proposal p) {
		
		Proposal proposal = proposalRepository.save(p);
		return proposal;
	}
	
}
