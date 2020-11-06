package com.soprabanking.ips.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Upvotes;

public interface UpvotesRepository extends JpaRepository<Upvotes, Long> {

    List<Upvotes> findAllByProposalId(Long proposalId);

    Upvotes findByUserIdAndProposalId(Long id, Long pid);
}
