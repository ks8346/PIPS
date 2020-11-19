package com.soprabanking.ips.daos;

import java.util.UUID;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soprabanking.ips.models.Token;
import com.soprabanking.ips.repositories.TokenRepository;

/**
 * This class is a Data Access Object Class for <a href="Token.html">{@link com.soprabanking.ips.models.Token}</a> made on tokens.It has the following methods
 * <ul>
 * 	<li>getToken</li>
 * 	<li>deleteToken</li>
 * 	<li>existsById</li>
 * 	<li>saveToken</li>
 *  <li>getById</li>
 * </ul>
 * @author kavsharma
 *
 */
@Component
public class TokenDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(TokenDAO.class);
	
	@Autowired
	private TokenRepository tokenRepository;
	/**
	 * This method is used to fetch the token by providing email id.
	 * @param username :username of string type{@link com.soprabanking.ips.models.Token}
	 * @return object of token
	 */
	
	public Token getToken(String username) {
		LOGGER.info("Inside TokenDAO : getToken(username) method");
		Token token = tokenRepository.getTokenByEmail(username);
		if (token == null) {
			throw new EntityNotFoundException();
		}
		else {
			return token;
		}
	}
	/**
	 * This method is used to delete the token by providing token id.
	 * @param id :id of uuid type{@link com.soprabanking.ips.models.Token}
	 
	 */
	
	
	public void deleteToken(UUID id) {
		LOGGER.info("Inside TokenDAO : deleteToken(id) method");
		tokenRepository.deleteById(id);
	}
	/**
	 * This method is used to fetch the token id.
	 * @param id :id of uuid type{@link com.soprabanking.ips.models.Token}
	 * @return id of the token which exists in the database
	 */
	
	public boolean exitsById(UUID id) {
		LOGGER.info("Inside TeamDAO : existsById(id) method");
		return tokenRepository.existsById(id);
	}
	/**
	 * This method is used to save the token.
	 * @param token :An object of <a href="Token.html">{@link com.soprabanking.ips.models.Token}</a>
	 */
	public void saveToken(Token token) {
		LOGGER.info("Inside TokenDAO : saveToken(token) method");
		tokenRepository.save(token);
	}
	/**
	 * This method is used to fetch the token id.
	 * @param id :id of uuid type{@link com.soprabanking.ips.models.Token}
	 * @return id of the token
	 */
	public Token getById(UUID id) {
		LOGGER.info("Inside TokenDAO : getById(id) method");
		return tokenRepository.findById(id).orElse(null);
	}
}
