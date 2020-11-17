package com.soprabanking.ips.services;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soprabanking.ips.daos.TokenDAO;
import com.soprabanking.ips.models.Token;

@Service
public class TokenService {
	
	@Autowired
	private TokenDAO tokenDao;
	
	@Autowired
	private ReentrantLock lock;
	
	public Token createToken(UUID id, String username) {
		
		Token token = new Token();
		token.setId(id);
		token.setEmail(username);
		return token;
		
	}
	
	public void deleteTokenByUsername(String username) {
		
		lock.lock();
		try {
			Token token = tokenDao.getToken(username);
			tokenDao.deleteToken(token.getId());
		}finally {
			lock.unlock();
		}
	}
	
	public void deleteTokenById(UUID id) {
		
		lock.lock();
		try {
			tokenDao.deleteToken(id);
		}finally {
			lock.unlock();
		}
		
	}
	
	public void saveToken(Token token) {
		
		tokenDao.saveToken(token);
	}
	
	public Token findTokenById(UUID id) {
		return tokenDao.getById(id);
	}

}
