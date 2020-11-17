package com.soprabanking.ips.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.soprabanking.ips.daos.CommentDAO;
import com.soprabanking.ips.daos.ProposalDAO;
import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.daos.UpvotesDAO;
import com.soprabanking.ips.daos.UserDAO;
import com.soprabanking.ips.models.Comment;
import com.soprabanking.ips.models.Proposal;
import com.soprabanking.ips.models.Team;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.utilities.DateUtil;
import com.soprabanking.ips.utilities.JsonUtil;

/**
 * Class representing Proposal Service that is responsible for providing the business logic for servicing the requests for Creation,Deletion and Retrieval of {@link Proposal} objects from the database ,received from the {@link com.soprabanking.ips.controllers.ProposalController} and {@link com.soprabanking.ips.controllers.FeedController} 
 * <p>Proposal Service provides a range of service methods each of which is responsible for processing various request for performing the following operations:</p>
 * <ol>
 * <li> Fetching list of all the proposals shared with a given Team for displaying them in the default Team feed.</li>
 * <li> Creating a new {@link Proposal} by a {@link User}.</li>
 * <li>Updating an existing {@link Proposal}.</li>
 * <li>Sharing a given proposal,or</li>
 * <li> Deleting an existing {@link Proposal}</li>
 * </ol>
 *<p> Service methods invokes the respective methods of {@link CommentDAO},{@link UpvotesDAO},{@link TeamDAO},{@link UserDAO} and {@link ProposalDAO} in order to return the required results to the {@link com.soprabanking.ips.controllers.ProposalController} and {@link com.soprabanking.ips.controllers.FeedController}</p>
 * @author araghav
 * @see com.soprabanking.ips.models.Proposal
 * @see com.soprabanking.ips.daos.CommentDAO
 * @see com.soprabanking.ips.daos.UserDAO
 * @see com.soprabanking.ips.daos.ProposalDAO
 * @see com.soprabanking.ips.daos.TeamDAO
 * @see com.soprabanking.ips.daos.UpvotesDAO
 * @see com.soprabanking.ips.controllers.ProposalController
 * @see com.soprabanking.ips.controllers.FeedController
 */
@Service
public class ProposalService {
	
	private static final Logger LOGGER = LogManager.getLogger(ProposalService.class);
	
	/**
	* {@link ProposalDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Proposal} objects by interacting with the persistence layer 
	*/
    @Autowired
    private ProposalDAO proposalDAO;

   /* @Autowired
    TeamRepository teamRepository;*/

    /**
	* {@link UserDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link User} objects by interacting with the persistence layer 
	*/
    @Autowired
    private UserDAO userDAO;

    /**
	* {@link TeamDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Team} objects by interacting with the persistence layer 
	*/
    @Autowired
    private TeamDAO teamDAO;

    /**
	* {@link CommentDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link Comment} objects by interacting with the persistence layer 
	*/
    @Autowired
    private CommentDAO commentDao;

    /**
	* {@link UpvotesDAO} object responsible for performing Creation ,Retrieval and Deletion of {@link com.soprabanking.ips.models.Upvotes} objects by interacting with the persistence layer 
	*/
    @Autowired
    private UpvotesDAO upvotesDAO;

    /**
     * Returns a List of all the {@link Proposal} objects shared with a given {@link Team} in response to the specified Request Body String containing the following:
     * <ol>
     * <li>A Start Date and an End Date to specify the time duration for filtering of {@link Proposal} objects based on their Creation dates.Only the proposals created in the specified time duration will be included in the result.</li>
     * <li>A Page value for specifying the Page Number in order to implement pagination of the resulting list of proposals. </li>
     * <li>A size value for specifying the number of proposals required on a given page(specified by Page value). </li>
     * <li>id of {@link com.soprabanking.ips.models.Team} specifying the team ,proposals shared with whose members have to be fetched.</li>
     * </ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, Start Date and End Dates specifying the time period of creation of every {@link Proposal} in the resulting list,Page value specifying the page number of the list of {@link Proposal} objects to be fetched,a Size value specifying the number of {@link Proposal} objects required on the given page and id value of {@link com.soprabanking.ips.models.Team} specifying the team,proposals shared with the members of whom have to be fetched)
     *  and then retrieves the subset of list of proposals created in the specified time duration ,created/sliced according to the specified Page and Size Values with the help of {@link ProposalDAO#getDefault(Team, Date, Date, Pageable)} method of {@link ProposalDAO}. </p>
     * @param body  String object representing the Request Body for fetching the List of all the Proposals stored in database
     * @return List of all the {@link Proposal} objects shared with a given {@link Team}  
     *@see com.soprabanking.ips.daos.ProposalDAO#getDefault(Team, Date, Date, Pageable)
     */
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

    /**
     * Deletes an existing {@link Proposal} object in response to the specified Request Body String containing the id value of the {@link Proposal} object to be deleted. 
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the {@link Proposal} which has to be deleted.) and then does the following :
     *<ol><li> Deletes all the {@link Comment} objects for the given {@link Proposal} with the help of {@link CommentDAO#fetchAllComments(Long)} and {@link CommentDAO#deleteComment(Long)} methods of {@link CommentDAO},</li>
     *<li> Deletes all the {@link com.soprabanking.ips.models.Upvotes} for the given {@link Proposal} with the help of {@link UpvotesDAO#fetchAllUpvotes(Long)} and {@link UpvotesDAO#deleteUpvote(com.soprabanking.ips.models.Upvotes)} of {@link UpvotesDAO} </li>
     *<li>Deletes the {@link Proposal} with a given id value with the help of {@link ProposalDAO#deleteProposal(Long)} method of {@link ProposalDAO}. </li></ol>
     * @param body String object representing the Request Body for deleting an existing Proposal.
     * @return A boolean true if the proposal is deleted successfully and false if delete could not be performed
     * @see com.soprabanking.ips.daos.CommentDAO#fetchAllComments(Long)
     * @see com.soprabanking.ips.daos.CommentDAO#deleteComment(Long)
     * @see com.soprabanking.ips.daos.UpvotesDAO#fetchAllUpvotes(Long)
     * @see com.soprabanking.ips.daos.UpvotesDAO#deleteUpvote(com.soprabanking.ips.models.Upvotes)
     */
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

    /**
     * Creates and returns a new {@link Proposal} object that is made by a particular {@link User} according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} which is being created,</li>
     * <li>id of the {@link User} who is creating the given {@link Proposal} and,</li>
     * <li>Title of the {@link Proposal}</li>
     * <li>Textual description of the {@link Proposal}.</li>
     * <li>List of teams to which the {@link Proposal} has to be forwarded.</li></ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the new {@link Proposal} which is being created,id of the {@link User} who is making the proposal,title of the proposal ,text of the proposal and the list of teams to which the new proposal has to be forwarded.)
     *  ,creates a new {@link Proposal} object containing a title and textual description forwarded to a number of teams made by the given {@link User} on current date and time ,save the newly created {@link Proposal} into the database with the help of {@link ProposalDAO#saveProposal(Proposal)} method of {@link ProposalDAO} and retrieves the saved {@link Proposal} object from the database.</p>
     * @param body String object representing the Request Body for creating a new Proposal
     * @return A newly created Proposal object 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     * @see com.soprabanking.ips.daos.ProposalDAO#saveProposal(Proposal)
     */
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

	/**
     * Updates and returns an existing {@link Proposal} object according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} which is being updated,</li>
     * <li>New/Updated Title of the {@link Proposal}</li>
     * <li>New/Updated Textual description of the {@link Proposal}.</li></ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the existing {@link Proposal} which is being updated,new title of the proposal , new description of the proposal.)
     *  ,saves the {@link Proposal} object containing the updated title and textual description into the database with the help of {@link ProposalDAO#saveProposal(Proposal)} method of {@link ProposalDAO} and retrieves the saved {@link Proposal} object from the database.</p>
     * @param body String object representing the Request Body for updating an existing Proposal
     * @return Updated Proposal object 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     * @see com.soprabanking.ips.daos.ProposalDAO#saveProposal(Proposal)
     */
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


	/**
     * Shares and returns an existing {@link Proposal} object according to the specified Request Body String containing the following: 
     * <ol><li>id value of the {@link Proposal} which is being shared,</li>
     * <li>List of teams with which the {@link Proposal} is being shared.</li></ol>
     * <p>This method converts the String argument representing the Request Body to a JSON request,retrieves request parameters and values from request body(in this case, id value of the existing {@link Proposal} which is being shared and the list of teams with which the proposal is being shared.)
     *  ,saves the {@link Proposal} object with the teams to which it is shared ,to the database with the help of {@link ProposalDAO#saveProposal(Proposal)} method of {@link ProposalDAO} and retrieves the saved {@link Proposal} object from the database.</p>
     * @param body String object representing the Request Body for sharing a given Proposal
     * @return Shared Proposal object 
     * @throws Exception if the Request Body is not in the correct format or if the request parameter values are invalid or not acceptable
     * @see com.soprabanking.ips.daos.ProposalDAO#saveProposal(Proposal)
     */
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
