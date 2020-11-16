package com.soprabanking.ips.services;

import java.util.Date;
import java.util.List;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;
/**
 * Class representing Feed Service that is responsible for providing the business logic for servicing the requests for retrieval of view based list of {@link Proposal} objects from the database ,received from the {@link com.soprabanking.ips.controllers.FeedController}
 * <p>Feed Service provides a range of service methods each of which is responsible for processing various request for performing the following operations:</p>
 * <ol>
 * <li>Fetching list of all the proposals stored in the database,and</li>
 * <li>Fetching list of all the proposals created by a particular {@link com.soprabanking.ips.models.User}</li>
 * </ol>
 * <p> Service methods invokes the respective methods of {@link ProposalDAO} in order to return the required results to the {@link com.soprabanking.ips.controllers.FeedController}</p> 
 * @author araghav
 *@see com.soprabanking.ips.models.Proposal
 *@see com.soprabanking.ips.models.User
 *@see com.soprabanking.ips.daos.ProposalDAO
 *@see com.soprabanking.ips.controllers.FeedController
 */
@Service
public class FeedService {

	/**
	 * {@link ProposalDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Proposal} objects by interacting with the persistence layer.
	 */
    @Autowired
    private ProposalDAO proposalDAO;

    Logger logger = LogManager.getLogger(FeedService.class);
    
    /**
     * Returns a List of all the {@link Proposal} objects stored in database in response to the specified Request Body String containing the following:
     * <ol>
     * <li>A Start Date and an End Date to specify the time duration for filtering of {@link Proposal} objects based on their Creation dates.Only the proposals created in the specified time duration will be included in the result.</li>
     * <li>A Page value for specifying the Page Number in order to implement pagination of the resulting list of proposals. </li>
     * <li>A size value for specifying the number of proposals required on a given page(specified by Page value). </li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, Start Date and End Dates specifying the time period of creation of every {@link Proposal} in the resulting list,Page value specifying the page number of the list of {@link Proposal} objects to be fetched and a Size value specifying the number of {@link Proposal} objects required on the given page)
     *  and then retrieves the subset of list of proposals created in the specified time duration ,created/sliced according to the specified Page and Size Values with the help of {@link ProposalDAO#fetchAllProposals(Date, Date, Pageable)} method of {@link ProposalDAO}. </p>
     * @param body  String object representing the Request Body for fetching the List of all the Proposals stored in database
     * @return List of all the {@link Proposal} objects stored in database 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     *@see com.soprabanking.ips.daos.ProposalDAO#fetchAllProposals(Date, Date, Pageable)
     */
    public List<Proposal> fetchAllProposals(String body) throws Exception {
        logger.info("Inside FeedService: fetchAllProposals() method");

        try {
            JsonNode jsonObj = JsonUtil.stringToJson(body);

            
            Date startDate = DateUtil.stringToISTDate(jsonObj.get("startDate").asText());
            Date endDate = DateUtil.stringToISTDate(jsonObj.get("endDate").asText());
            int page = Integer.parseInt(jsonObj.get("page").asText());
            int size = Integer.parseInt(jsonObj.get("size").asText());
            
            if (startDate.after(endDate))
                throw new Exception();

            Pageable pageable = PageRequest.of(page, size);
            Slice<Proposal> result = proposalDAO.fetchAllProposals(startDate, endDate, pageable);
            return result.getContent();
        } catch (Exception ex) {
            logger.error("Error caught",ex);

            throw new Exception();
        }
    }
   
    /**
     * Returns a List of all the {@link Proposal} objects created by a particular {@link com.soprabanking.ips.models.User} in response to the specified Request Body String containing the following:
     * <ol>
     * <li>A Start Date and an End Date to specify the time duration for filtering of {@link Proposal} objects based on their Creation dates.Only the proposals created in the specified time duration will be included in the result.</li>
     * <li>A Page value for specifying the Page Number in order to implement pagination of the resulting list of proposals. </li>
     * <li>A size value for specifying the number of proposals required on a given page(specified by Page value). </li>
     * <li>id of {@link com.soprabanking.ips.models.User} specifying the user ,proposals created by whom have to be fetched.</li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, Start Date and End Dates specifying the time period of creation of every {@link Proposal} in the resulting list,Page value specifying the page number of the list of {@link Proposal} objects to be fetched , id value of {@link com.soprabanking.ips.models.User} specifying the user whose proposals have to be fetched and a Size value specifying the number of {@link Proposal} objects required on the given page)
     *  and then retrieves the subset of list of proposals created by the specified {@link com.soprabanking.ips.models.User} in the specified time duration ,created/sliced according to the specified Page and Size Values with the help of {@link ProposalDAO#fetchUserProposals(Long, Date, Date, Pageable)} method of {@link ProposalDAO}. </p>
     * @param body  String object representing the Request Body for fetching the List of all the Proposals created by a particular {@link com.soprabanking.ips.models.User}
     * @return List of all the {@link Proposal} that were created by the specified {@link com.soprabanking.ips.models.User}
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     *@see com.soprabanking.ips.daos.ProposalDAO#fetchUserProposals(Long, Date, Date, Pageable)
     */

    public List<Proposal> fetchUserProposals(String body) throws Exception {

        try {
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

            return result.getContent();


        } catch (Exception ex) {
        	ex.printStackTrace();
            throw new Exception();

        }
    }
}
