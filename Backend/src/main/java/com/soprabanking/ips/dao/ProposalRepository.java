package com.soprabanking.ips.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.soprabanking.ips.entities.Proposal;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
	

}
