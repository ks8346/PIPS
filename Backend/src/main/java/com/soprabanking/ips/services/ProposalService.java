package com.soprabanking.ips.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.soprabanking.ips.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;

@Service
public class ProposalService {
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
            return !proposals.isEmpty() ? proposals : null;

        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteProposal(String body){
    	try{
			JsonNode jsonObj = JsonUtil.stringToJson(body);
			Long proposalId = Long.parseLong(jsonObj.get("id").asText());
			//Proposal proposal=proposalDAO.getById(proposalId);
			commentDao.fetchAllComments(proposalId).forEach(comment -> commentDao.deleteComment(comment.getId()));
			upvotesDAO.fetchAllUpvotes(proposalId).forEach(upvotesDAO::deleteUpvote);
			proposalDAO.deleteProposal(proposalId);
			return true;
		}
    	catch (Exception e){
    		return false;
		}
	}

	public Proposal saveProposal(String body) throws Exception {
		
		try {
			
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
			return addedProposal;

			 } 
		catch (Exception e) {
			throw new Exception();
		}
	}

	
	public Proposal updateProposal(String body) throws Exception {
		
		try {
			
			JsonNode jsonObj = JsonUtil.stringToJson(body);
			
			Long key = Long.parseLong(jsonObj.get("key").asText());
			String title = jsonObj.get("title").asText();
			String desc = jsonObj.get("description").asText();
			
			
			
			Proposal proposal=proposalDAO.getById(key);
			
			
				
			proposal.setTitle(title);
			proposal.setDescription(desc);
			
			
			
			Proposal addedProposal = proposalDAO.saveProposal(proposal);
			return addedProposal;
		}
		catch(Exception ex)
		{
			throw new Exception();
		}
			
	 }


    public Proposal shareProposal(String body) throws Exception {
        try {
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Long pid = Long.parseLong(jsonObj.get("id").asText());
            JsonNode jnode = jsonObj.get("teams");

            Set<Team> teams = new HashSet<>();

            for (JsonNode j : jnode) {
                Long tid = (Long.parseLong(j.get("id").asText()));
                //String tname=(j.get("name").asText());
                teams.add(teamDAO.getTeam(tid));
            }

            Proposal proposal = proposalDAO.getById(pid);

            for (Team t : teams) {
                proposal.addTeam(t);
            }

            Proposal sharedProposal = proposalDAO.saveProposal(proposal);
            return sharedProposal;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
