package com.soprabanking.ips.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.soprabanking.ips.daos.TokenDAO;
import com.soprabanking.ips.models.Token;

@SpringBootTest
class TokenServiceTest {
	
	private Token token;
	private UUID id;
	
	@MockBean
	private TokenDAO tokenDao;
	
	@MockBean
	private ReentrantLock lock;
	
	@Autowired
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
		verifyNoInteractions(tokenDao, lock);
	}
	
	@Test
	void deleteTokenByUsernameSuccessfulltest() {
		
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		when(tokenDao.getToken("nk@gmail.com")).thenReturn(token);
		Mockito.doNothing().when(tokenDao).deleteToken(token.getId());
		assertTrue(tokenService.deleteTokenByUsername("nk@gmail.com"));
		verify(lock).lock();
		verify(lock).unlock();
		verify(tokenDao).getToken(anyString());
		verify(tokenDao).deleteToken(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void deleteTokenByUsernameUnsuccessfulltest1() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		when(tokenDao.getToken("nk@gmail.com")).thenThrow(EntityNotFoundException.class);
		assertFalse(tokenService.deleteTokenByUsername("nk@gmail.com"));
		verify(lock).lock();
		verify(lock).unlock();
		verify(tokenDao).getToken(anyString());
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void deleteTokenByUsernameUnsuccessfulltest2() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		when(tokenDao.getToken("nk@gmail.com")).thenReturn(token);
		Mockito.doThrow(new IllegalArgumentException()).when(tokenDao).deleteToken(any(UUID.class));
		assertFalse(tokenService.deleteTokenByUsername("nk@gmail.com"));
		verify(lock).lock();
		verify(lock).unlock();
		verify(tokenDao).getToken(anyString());
		verify(tokenDao).deleteToken(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void deleteTokenByIdSuccessfulltest() {
		
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		Mockito.doNothing().when(tokenDao).deleteToken(id);
		assertTrue(tokenService.deleteTokenById(id));
		verify(lock).lock();
		verify(lock).unlock();
		verify(tokenDao).deleteToken(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void deleteTokenByIdUnsuccessfulltest() {
			
		Mockito.doNothing().when(lock).lock();
		Mockito.doNothing().when(lock).unlock();
		Mockito.doThrow(new IllegalArgumentException()).when(tokenDao).deleteToken(id);
		assertFalse(tokenService.deleteTokenById(id));
		verify(lock).lock();
		verify(lock).unlock();
		verify(tokenDao).deleteToken(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void saveTokentest() {

		Mockito.doNothing().when(tokenDao).saveToken(token);
		assertDoesNotThrow(()->tokenService.saveToken(token));
		verify(tokenDao).saveToken(any(Token.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
	
	@Test
	void findTokentest1() {
		
		when(tokenDao.getById(id)).thenReturn(token);
		assertEquals(token.getEmail(), tokenService.findTokenById(id).getEmail());
		verify(tokenDao).getById(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}

	@Test
	void findTokentest2() {
		
		when(tokenDao.getById(id)).thenReturn(null);
		assertThrows(Exception.class, ()->tokenService.findTokenById(id));
		verify(tokenDao).getById(any(UUID.class));
		verifyNoMoreInteractions(tokenDao, lock);
	}
}
