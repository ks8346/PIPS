package com.soprabanking.ips.services;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;

@Service
public class FeedService {
	
	private static final Logger LOGGER = LogManager.getLogger(FeedService.class);

    @Autowired
    private ProposalDAO proposalDAO;

    public List<Proposal> fetchAllProposals(String body) throws Exception {

        try {
        	LOGGER.info("Inside FeedService: fetchAllProposals() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);

            
            Date startDate = DateUtil.stringToISTDate(jsonObj.get("startDate").asText());
            Date endDate = DateUtil.stringToISTDate(jsonObj.get("endDate").asText());
            int page = Integer.parseInt(jsonObj.get("page").asText());
            int size = Integer.parseInt(jsonObj.get("size").asText());
            
            if (startDate.after(endDate))
                throw new Exception();

            Pageable pageable = PageRequest.of(page, size);
            Slice<Proposal> result = proposalDAO.fetchAllProposals(startDate, endDate, pageable);
            
            LOGGER.info("Inside FeedService : fetchAllProposals() SUCCESS");
            return result.getContent();
        } catch (Exception ex) {
        	LOGGER.error("Inside FeedService : fetchAllProposals() FAILURE", ex);
            throw new Exception();
        }
    }

    public List<Proposal> fetchUserProposals(String body) throws Exception {

        try {
        	LOGGER.info("Inside FeedService : fetchUserProposals() method");
            JsonNode jsonObj = JsonUtil.stringToJson(body);
            Date startDate = DateUtil.stringToISTDate(jsonObj.get("startDate").asText());
            Date endDate = DateUtil.stringToISTDate(jsonObj.get("endDate").asText());
            int page = Integer.parseInt(jsonObj.get("page").asText());
            int size = Integer.parseInt(jsonObj.get("size").asText());
            Long userId = Long.parseLong(jsonObj.get("userId").asText());

            if (startDate.after(endDate))
            	throw new Exception();

            Pageable pageable = PageRequest.of(page, size);
            Slice<Proposal> result = proposalDAO.fetchUserProposals(userId, startDate, endDate, pageable);

            LOGGER.info("Inside FeedService : fetchUserProposals() SUCCESS");
            return result.getContent();


        } catch (Exception ex) {
        	LOGGER.error("Inside FeedService : fetchUserProposals() FAILURE", ex);
            throw new Exception();

        }
    }
}
