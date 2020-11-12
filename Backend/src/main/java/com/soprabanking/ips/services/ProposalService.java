package com.soprabanking.ips.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;

@Service
public class ProposalService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProposalService.class);
	
    @Autowired
    private ProposalDAO proposalDAO;

   /* @Autowired
    TeamRepository teamRepository;*/

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TeamDAO teamDAO;

    @Autowired
    private CommentDAO commentDao;

    @Autowired
    private UpvotesDAO upvotesDAO;

    public List<Proposal> getDefault(String body) {
        try {
        	LOGGER.info("Inside ProposalService : getDefault() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);

            Date startDate = DateUtil.stringToISTDate(jsonObj.get("startDate").asText());
            Date endDate = DateUtil.stringToISTDate(jsonObj.get("endDate").asText());
            int page = Integer.parseInt(jsonObj.get("page").asText());
            int size = Integer.parseInt(jsonObj.get("size").asText());
            Long teamId = Long.parseLong(jsonObj.get("teamId").asText());
            if(startDate.after(endDate))
                return null;
            Team team = teamDAO.getTeam(teamId);
            List<Proposal> proposals = proposalDAO.getDefault(team, startDate, endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"));
            
            LOGGER.info("Inside ProposalService : getDefault() SUCCESS");
            return !proposals.isEmpty() ? proposals : null;

        } catch (Exception e) {
        	LOGGER.error("Inside ProposalService : getDefault() FAILURE", e);
            return null;
        }
    }

    public boolean deleteProposal(String body){
    	try{
    		LOGGER.info("Inside ProposalService : deleteProposal() method");
			JsonNode jsonObj = JsonUtil.stringToJson(body);
			Long proposalId = Long.parseLong(jsonObj.get("id").asText());
			//Proposal proposal=proposalDAO.getById(proposalId);
			commentDao.fetchAllComments(proposalId).forEach(comment -> commentDao.deleteComment(comment.getId()));
			upvotesDAO.fetchAllUpvotes(proposalId).forEach(upvotesDAO::deleteUpvote);
			proposalDAO.deleteProposal(proposalId);
			
			LOGGER.info("Inside ProposalService : deleteProposal() SUCCESS");
			return true;
		}
    	catch (Exception e){
    		LOGGER.error("Inside ProposalService : deleteProposal() FAILURE", e);
    		return false;
		}
	}

	public Proposal saveProposal(String body) throws Exception {
		
		try {
			LOGGER.info("Inside ProposalService : saveProposal() method");
			JsonNode jsonObj = JsonUtil.stringToJson(body);
			
			String key = jsonObj.get("key").asText();
			String title = jsonObj.get("title").asText();
			String desc = jsonObj.get("description").asText();
			Long userId = Long.parseLong(jsonObj.get("userId").asText());
			
			JsonNode jnode = jsonObj.get("teams");
			
			Set<Team> teams = new HashSet<>();
			
			for(JsonNode j : jnode) {
				Team t = new Team();
				
				t.setId(Long.parseLong(j.get("id").asText()));
				t.setName(j.get("name").asText());
				teams.add(teamDAO.getTeam(Long.parseLong(j.get("id").asText())));
			}
			
			//System.out.println(teams);
			User user = userDAO.getById(userId);
			Proposal proposal = new Proposal();
			proposal.setTitle(title);
			proposal.setDescription(desc);
			proposal.setUpvotesCount((long)0);
			proposal.setUser(user);
			proposal.setCreationDate(new Date());
			proposal.setTeams(teams);

			//System.out.println(proposal);
			Proposal addedProposal = proposalDAO.saveProposal(proposal);
			
			LOGGER.info("Inside ProposalService : saveProposal() SUCCESS");
			return addedProposal;

			 } 
		catch (Exception e) {
			LOGGER.error("Inside ProposalService : saveProposal() FAILURE", e);
			throw new Exception();
		}
	}

	
	public Proposal updateProposal(String body) throws Exception {
		
		try {
			LOGGER.info("Inside ProposalService : updateProposal() method");
			JsonNode jsonObj = JsonUtil.stringToJson(body);
			
			Long key = Long.parseLong(jsonObj.get("key").asText());
			String title = jsonObj.get("title").asText();
			String desc = jsonObj.get("description").asText();
			
			Proposal proposal=proposalDAO.getById(key);
			proposal.setTitle(title);
			proposal.setDescription(desc);
	
			Proposal addedProposal = proposalDAO.saveProposal(proposal);
			LOGGER.info("Inside ProposalService : updateProposal() SUCCESS");
			return addedProposal;
		}
		catch(Exception ex)
		{
			LOGGER.error("Inside ProposalService : updateProposal() FAILURE", ex);
			throw new Exception();
		}
			
	 }


    public Proposal shareProposal(String body) throws Exception {

        try {
        	LOGGER.info("Inside ProposalService : shareProposal() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            JsonNode jnode = jsonObj.get("teams");

            Set<Team> teams = new HashSet<>();

            for (JsonNode j : jnode) {
                Long tid = (Long.parseLong(j.get("id").asText()));
                teams.add(teamDAO.getTeam(tid));
            }

            Proposal proposal = proposalDAO.getById(pid);

            for (Team t : teams) {
                proposal.addTeam(t);
            }

            Proposal sharedProposal = proposalDAO.saveProposal(proposal);
            LOGGER.info("Inside ProposalService : shareProposal() SUCCESS");
            return sharedProposal;
        } catch (Exception e) {
        	LOGGER.error("Inside ProposalService : shareProposal() FAILURE", e);
            throw new Exception();
        }
    }

}
