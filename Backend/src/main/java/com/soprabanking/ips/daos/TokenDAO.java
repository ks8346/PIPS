package com.soprabanking.ips.daos;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.repositories.TokenRepository;

@Component
public class TokenDAO {
	
	@Autowired
	private TokenRepository tokenRepository;
	
	public Token getToken(String username) {
		Token token = tokenRepository.getTokenByEmail(username);
		if (token == null) {
			throw new EntityNotFoundException();
		}
		else {
			return token;
		}
	}
	
	public void deleteToken(UUID id) {
		tokenRepository.deleteById(id);
	}
	
	public boolean exitsById(UUID id) {
		return tokenRepository.existsById(id);
	}
	
	public void saveToken(Token token) {
		tokenRepository.save(token);
	}

	public Token getById(UUID id) {
		return tokenRepository.getOne(id);
	}
}
