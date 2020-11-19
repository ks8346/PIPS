package com.soprabanking.ips.services;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class TimerServiceTest {

	@MockBean
	private TokenService tokenService;
	
	@MockBean
	private Timer timer;
	
	@Autowired
	private TimerService timerService;

	@Test
	void createTimertest() {

		assertNotNull(timerService.createTimer(UUID.randomUUID()));
		verifyNoInteractions(tokenService, timer);
	}
	
	@Test
	void scheduleTimerSuccessfulltest() {
		
		TimerTask task = timerService.createTimer(UUID.randomUUID());
		Mockito.doNothing().when(timer).schedule(any(TimerTask.class), anyLong());
		assertDoesNotThrow(()->timerService.scheduleTimer(task, 60000L));
		verify(timer).schedule(any(TimerTask.class), anyLong());
		verifyNoMoreInteractions(timer, tokenService);
	}
	
	@Test
	void scheduleTimerUnsuccessfullTest() {
		Mockito.doThrow(new NullPointerException()).when(timer).schedule(isNull(), anyLong());
		assertThrows(Exception.class, ()->timerService.scheduleTimer(null, 60000L));
		verify(timer).schedule(isNull(), anyLong());
		verifyNoMoreInteractions(timer, tokenService);
	}

}
