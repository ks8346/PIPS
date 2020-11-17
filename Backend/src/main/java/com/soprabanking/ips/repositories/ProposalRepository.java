package com.soprabanking.ips.repositories;

import java.util.Date;
import java.util.List;

import com.soprabanking.ips.models.Team;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Proposal;

import javax.validation.constraints.NotNull;
/**
 * This is the Proposal Repository interface which extends the JpaRepository.It accesses the <a href="Proposal.html">{@link com.soprabanking.ips.models.Proposal}</a> model
 * @author mojha
 * @see com.soprabanking.ips.models.Proposal
 * @see com.soprabanking.ips.models.Team
 *
 */

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

	/**
	 * This method is used to find a slice of proposals by a pageable variable for pagination so that our proposals are visible in a stream
	 * @param pageable : A pageable type variable to show the proposals in a continuous stream
	 * @return a slice of proposals by the pagination variable
	 */
    Slice<Proposal> findSliceBy(Pageable pageable);
    /**
     * This method is used to find a slice between the given start and end date of all the proposals in the database
     * @param startDate :the start date from which proposals are shown
     * @param endDate :the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a slice of all proposals between the given start and end date
     */

    Slice<Proposal> findAllByCreationDateBetweenOrderByUpvotesCountDesc(Date startDate, Date endDate, Pageable pageable);

    /**
     * This method is used to find a slice between the given start and end date of all the proposals made by the user whose id is passed in the method in the database
     * @param id :the user  id
     * @param startDate :the start date from which proposals are shown
     * @param endDate:the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a slice of all user proposals between the given start and end date
     */

    Slice<Proposal> findAllByUserIdAndCreationDateBetweenOrderByUpvotesCountDesc(Long id, Date startDate, Date endDate, Pageable pageable);

    /**
     * This method is used to find a slice between the given start and end date of all the proposals marked to the given team id in the database
     * @param team : Team id
     * @param startDate :the start date from which proposals are shown
     * @param endDate :the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a slice of all proposals marked to the team between the given start and end date
     */
    List<Proposal> findByTeamsAndCreationDateBetween(Team team, Date startDate, Date endDate, Pageable pageable);

    //void deleteById(Long id);

}
