package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprabanking.ips.daos.TokenDAO;
import com.soprabanking.ips.models.Token;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {
	
	private Token token;
	private UUID id;
	
	@Mock
	private TokenDAO tokenDao;
	
	@Mock
	private ReentrantLock lock;
	
	@InjectMocks
	private TokenService tokenService;
	
	@BeforeEach
	void init() {

		id = new UUID(1L, 1L);
		
		token = new Token();
		token.setId(id);
		token.setEmail("nk@gmail.com");
	}
	
	@Test
	void createTokentest() {
		
		assertEquals(token.getEmail(), tokenService.createToken(id, "nk@gmail.com").getEmail());
	
	}
	
	@Test
	void deleteTokenByUsernameSuccessfulltest() {
		
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		
		when(tokenDao.getToken("nk@gmail.com")).thenReturn(token);
		Mockito.doNothing().when(tokenDao).deleteToken(token.getId());
		
		assertDoesNotThrow(()->tokenService.deleteTokenByUsername("nk@gmail.com"));
	
	}
	
	@Test
	void deleteTokenByUsernameUnsuccessfulltest1() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		
		when(tokenDao.getToken("nk@gmail.com")).thenThrow(EntityNotFoundException.class);
		
		assertThrows(Exception.class, ()->tokenService.deleteTokenByUsername("nk@gmail.com"));
	
	}
	
	@Test
	void deleteTokenByUsernameUnsuccessfulltest2() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		
		when(tokenDao.getToken("nk@gmail.com")).thenReturn(new Token());
		Mockito.doThrow(new IllegalArgumentException()).when(tokenDao).deleteToken(any(UUID.class));
		
		assertThrows(Exception.class, ()->tokenService.deleteTokenByUsername("nk@gmail.com"));
	
	}
	
	@Test
	void deleteTokenByIdSuccessfulltest() {
		
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		
		Mockito.doNothing().when(tokenDao).deleteToken(id);
		
		assertDoesNotThrow(()->tokenService.deleteTokenById(id));
	
	}
	
	@Test
	void deleteTokenByIdUnsuccessfulltest() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		
		Mockito.doThrow(new IllegalArgumentException()).when(tokenDao).deleteToken(id);
		
		assertThrows(Exception.class, ()->tokenService.deleteTokenById(id));
	
	}
	
	@Test
	void saveTokentest() {

		Mockito.doNothing().when(tokenDao).saveToken(token);
		assertDoesNotThrow(()->tokenService.saveToken(token));
	
	}
	
	@Test
	void findTokentest() {
		
		when(tokenDao.getById(id)).thenReturn(token);
		
		assertEquals(token.getEmail(), tokenService.findTokenById(id).getEmail());
	}

}
