package com.soprabanking.ips.daos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Upvotes;
import com.soprabanking.ips.repositories.UpvotesRepository;

@Component
public class UpvotesDAO 
{
	@Autowired
	private UpvotesRepository upvotesRepository;
	
	public Upvotes createUpvote(Upvotes upvote)
	{
		return upvotesRepository.save(upvote);
	}
	
	public List<Upvotes> fetchAllUpvotes(Long proposalId)
	{
		return upvotesRepository.findAllByProposalId(proposalId);
	}
	
	public void deleteUpvote(Upvotes upvote) {
		
		upvotesRepository.delete(upvote);
		
	}
	
	public Upvotes getUpvoteforUserIdAndProposalId(Long uid,Long pid)
	{
		return upvotesRepository.findByUserIdAndProposalId(uid, pid);
	}
	
	
}
