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
/**
 * This class is a Data Access Object Class for Proposals made.It has the following methods
 * <ul>
 * 	<li>fetchAllProposals</li>
 * <li>getDefault</li>
 * <li>fetchUserProposals</li>
 * <li>deleteProposal</li>
 * <li>saveProposal</li>
 * </ul>
 * @author mojha
 *
 */
@Component
public class ProposalDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(ProposalDAO.class);

    @Autowired
    private ProposalRepository proposalRepository;

    /**
     * A method to get a slice of all the proposals in the database between give start and end dates
     * @param startDate :the start date from which proposals are shown
     * @param endDate :the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a slice of all proposals between the given start and end date
     */
    public Slice<Proposal> fetchAllProposals(Date startDate, Date endDate, Pageable pageable) {

    	LOGGER.info("Inside ProposalDAO : fetchAllProposals() method");
        return proposalRepository.findAllByCreationDateBetweenOrderByUpvotesCountDesc(startDate, endDate, pageable);

    }

    /**
     * This is the default view on the landing page. I shows the teams proposals by the given start and end date
     * @param team : Team id
     * @param start :the start date from which proposals are shown
     * @param end :the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a list of all proposals marked to the team between the given start and end date
     */

    public List<Proposal> getDefault(Team team, Date start, Date end, Pageable pageable) {
    	
    	LOGGER.info("Inside ProposalDAO : getDefault() method");
        return proposalRepository.findByTeamsAndCreationDateBetween(team, start, end, pageable);

    }

    /**
     * It shows all of user proposals by the given start and end date
     * @param id :the user  id
     * @param startDate :the start date from which proposals are shown
     * @param endDate:the end date till which the proposals are shown
     * @param pageable : A pageable type variable to show the proposals in a continuous stream
     * @return a slice of all user proposals between the given start and end date
     */
    public Slice<Proposal> fetchUserProposals(Long id, Date startDate, Date endDate, Pageable pageable) {

    	LOGGER.info("Inside ProposalDAO : fetchUserProposals() method");
        return proposalRepository.findAllByUserIdAndCreationDateBetweenOrderByUpvotesCountDesc(id, startDate, endDate, pageable);
    }

    /**
     * It gives us the proposal we need by providing the proposal id
     * @param id:proposal id
     * @return the one proposal
     */
    public Proposal getById(Long id) {
    	
    	LOGGER.info("Inside ProposalDAO : getById() method");
        return proposalRepository.getOne(id);

    }
    /**
     * deletes the proposal by the id given
     * @param id: proposal id
     */

    public void deleteProposal(Long id) {
    	
    	LOGGER.info("Inside ProposalDAO : deleteProposal() method");
        proposalRepository.deleteById(id);
    }
    /**
     * This method saves the proposal 
     * @param p: Proposal object
     * @return a proposal which has been saved
     */

    public Proposal saveProposal(Proposal p) {

    	LOGGER.info("Inside ProposalDAO : saveProposal() method");
        Proposal proposal = proposalRepository.save(p);
        return proposal;
    }

}
