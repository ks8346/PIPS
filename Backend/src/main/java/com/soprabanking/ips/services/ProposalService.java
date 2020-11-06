package com.soprabanking.ips.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;

@Service
public class ProposalService {
    @Autowired
    ProposalDAO proposalDAO;

   /* @Autowired
    TeamRepository teamRepository;*/
    
    @Autowired
    UserDAO userDAO;

    @Autowired
    TeamDAO teamDAO;

    public List<Proposal> getDefault(String body) {
        try {
            
        	JsonNode jsonObj = JsonUtil.stringToJson(body);
			
			Date startDate = DateUtil.stringToISTDate(jsonObj.get("startDate").asText());
			Date endDate = DateUtil.stringToISTDate(jsonObj.get("endDate").asText());
			int page = Integer.parseInt(jsonObj.get("page").asText());
			int size = Integer.parseInt(jsonObj.get("size").asText());
			Long teamId = Long.parseLong(jsonObj.get("teamId").asText());
			
			Team team = teamDAO.getTeam(teamId);
        	List<Proposal> proposals = proposalDAO.getDefault(team, startDate, endDate, PageRequest.of(page, size, Sort.Direction.DESC, "upvotesCount"));

            return !proposals.isEmpty() ? proposals : null;

        }
        catch (Exception e){
            return null;
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
			
			
			//System.out.println(proposal);
			

			 } 
		catch (Exception e) {
			throw new Exception();
		}
	}
}
