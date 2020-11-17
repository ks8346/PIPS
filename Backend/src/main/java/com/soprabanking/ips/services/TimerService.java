package com.soprabanking.ips.services;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TeamDAO;
import com.soprabanking.ips.models.Team;
/** 
 * Timer Service
 * This is a service class
 * this class is used to set the timer
 * @author kavsharma
 * @see com.soprabanking.ips.services.TokenService
 * 
 * 
 */
@Service
public class TimerService {
	/**
	* {@link tokenService} object responsible for creating and deleting tokens {@link Token} objects by interacting with the persistence layer 
	*/
	@Autowired
	private TokenService tokenService;
	/**
	* {@link Timer} object responsible for setting the timer.
	*/
	@Autowired
	private Timer timer;
	/** 
	 * This method creates the timer.
	 * @param id: id of uuid type is the token id.
	 * @return object of the TimerTask
	 * 
	 */

	public TimerTask createTimer(UUID id) {
		
		return new TimerTask() {
            public void run() {
               tokenService.deleteTokenById(id);
            }
        };
	}
	/** 
	 * This method sets the timer.
	 * @param task: object of the TimeTask.
	 * @param delay: delay of long type
	 * 
	 */
	
	
	public void scheduleTimer(TimerTask task, Long delay) {
		
		timer.schedule(task, delay);
	}

}
