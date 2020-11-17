package com.soprabanking.ips.services;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TimerServiceTest {

	@Mock
	private TokenService tokenService;
	
	@Mock
	private Timer timer;
	
	@InjectMocks
	private TimerService timerService;

	@Test
	void createTimertest() {

		assertNotNull(timerService.createTimer(UUID.randomUUID()));
		
	}
	
	@Test
	void scheduleTimerSuccessfulltest() {
		
		TimerTask task = timerService.createTimer(UUID.randomUUID());
		
		Mockito.doNothing().when(timer).schedule(task, 60000L);
		
		assertDoesNotThrow(()->timerService.scheduleTimer(task, 60000L));
		
	}
	
	@Test
	void scheduleTimerUnsuccessfullTest() {
		Mockito.doThrow(new NullPointerException()).when(timer).schedule(null, 60000L);
		assertThrows(Exception.class, ()->timerService.scheduleTimer(null, 60000L));
	}

}
