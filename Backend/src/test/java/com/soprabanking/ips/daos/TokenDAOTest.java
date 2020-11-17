package com.soprabanking.ips.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.repositories.TokenRepository;

@ExtendWith(MockitoExtension.class)
class TokenDAOTest {
	
	private Token token;
	private UUID id;
	
	@Mock
	private TokenRepository tokenRepository;
	
	@InjectMocks
	private TokenDAO tokenDao;

	@BeforeEach
	void setUp() throws Exception {
		
		id = new UUID(1L, 1L);
		
		token = new Token();
		token.setId(id);
		token.setEmail("nk@gmail.com");
	}
	
	@Test
	void getTokentest1() {
		
		when(tokenRepository.getTokenByEmail("nk@gmail.com")).thenReturn(null);
		assertThrows(EntityNotFoundException.class, ()->tokenDao.getToken("nk@gmail.com"));
	}
	
	@Test
	void getTokentest2() {
		
		when(tokenRepository.getTokenByEmail("nk@gmail.com")).thenReturn(token);
		assertEquals(token.getEmail(), tokenDao.getToken("nk@gmail.com").getEmail());
	}
	
	@Test
	void deleteTokentest() {
		
		Mockito.doNothing().when(tokenRepository).deleteById(any(UUID.class));
		assertDoesNotThrow(()->tokenDao.deleteToken(id));
	}
	
	@Test
	void existByIdtest() {
		
		when(tokenRepository.existsById(any(UUID.class))).thenReturn(true);
		assertTrue(tokenDao.exitsById(id));
	}
	
	@Test
	void saveTokentest() {
		
		when(tokenRepository.save(any(Token.class))).thenReturn(token);
		assertDoesNotThrow(()->tokenDao.saveToken(new Token()));
	}
	
	@Test
	void getTokenByIdtest() {
		
		when(tokenRepository.getOne(any(UUID.class))).thenReturn(token);
		assertEquals(token.getEmail(), tokenDao.getById(id).getEmail());
	}

}
