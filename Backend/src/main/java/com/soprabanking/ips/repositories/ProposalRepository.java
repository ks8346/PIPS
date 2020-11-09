package com.soprabanking.ips.repositories;

import java.util.Date;
import java.util.List;

import com.soprabanking.ips.models.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Proposal;

import javax.validation.constraints.NotNull;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    Slice<Proposal> findSliceBy(Pageable pageable);

    Slice<Proposal> findAllByCreationDateBetweenOrderByUpvotesCountDesc(Date startDate, Date endDate, Pageable pageable);


    Slice<Proposal> findAllByUserIdAndCreationDateBetweenOrderByUpvotesCountDesc(Long id, Date startDate, Date endDate, Pageable pageable);

    List<Proposal> findByTeamsAndCreationDateBetween(Team team, Date startDate, Date endDate, Pageable pageable);

    //void deleteById(Long id);

}
