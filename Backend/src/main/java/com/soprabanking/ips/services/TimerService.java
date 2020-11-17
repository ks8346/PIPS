package com.soprabanking.ips.services;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerService {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private Timer timer;
	
	public TimerTask createTimer(UUID id) {
		
		return new TimerTask() {
            public void run() {
               tokenService.deleteTokenById(id);
            }
        };
	}
	
	public void scheduleTimer(TimerTask task, Long delay) {
		
		timer.schedule(task, delay);
	}

}
