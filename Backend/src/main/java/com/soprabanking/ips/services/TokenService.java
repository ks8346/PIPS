package com.soprabanking.ips.services;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.soprabanking.ips.daos.TokenDAO;
import com.soprabanking.ips.models.Token;
/** 
 * Token Service
 * This is a service class
 * this class is used to create token and delete token.
 * @author kavsharma
 * @see com.soprabanking.ips.models.Token
 * @see com.soprabanking.ips.daos.TokenDAO
 * 
 */
@Service
public class TokenService {
	/**
	* {@link TokenDAO} object responsible for creating token, getting token, saving the token, getting the id of the token {@link Token} objects by interacting with the persistence layer 
	*/
	
	private static final Logger LOGGER = LogManager.getLogger(TokenService.class);
	
	@Autowired
	private TokenDAO tokenDao;
	/**
	* {@link ReentrantLock} object responsible for locking the items.
	*/
	@Autowired
	private ReentrantLock lock;
	/** 
	 * This method sends the reset link to the registered email id.
	 * @param id: id of uuid type
	 * @param username: username of string name
	 * @return object of token.
	 * 
	 */

	
	public Token createToken(UUID id, String username) {
		LOGGER.info("Inside TokenService " + ": createToken(id,username) method");
		Token token = new Token();
		token.setId(id);
		token.setEmail(username);
		return token;
		
	}
	/** 
	 * This method sends the reset link to the registered email id.
	 * @param username: username of string type
	 * @return boolean
	 * 
	 */

	public boolean deleteTokenByUsername(String username) {
	       
        try {
        	LOGGER.info("Inside TokenService " + ": deleteTokenByUsername() method SUCCESS");
            lock.lock();
            Token token = tokenDao.getToken(username);
            tokenDao.deleteToken(token.getId());
            return true;
        }catch(Exception e) {
        	LOGGER.error("Inside TokenService : deleteTokenByUsername() method FAILURE", e);
            return false;
        }finally {
            lock.unlock();
        }
    }
	/** 
	 * This method sends the reset link to the registered email id.
	 * @param id: id of uuid type is the token id.
	 * @return boolean 
	 * 
	 */

		
	public boolean deleteTokenById(UUID id) {
	       
        try {
        	LOGGER.info("Inside TokenService " + ": deleteTokenById() method SUCCESS");
            lock.lock();
            tokenDao.deleteToken(id);
            return true;
        }catch(Exception e) {
        	LOGGER.error("Inside TokenService " + ": deleteTokenById() method FAILURE", e);
            return false;
        }finally{
            lock.unlock();
        }
       
    }
	/** 
	 * This method sends the reset link to the registered email id.
	 * @param token: An Object of the <a href="Token.html">{@link com.soprabanking.ips.models.Token}</a>
	 * 
	 */

	
	public void saveToken(Token token) {
		LOGGER.info("Inside TokenService " + ": saveToken(token) method");
		tokenDao.saveToken(token);
	}
	/** 
	 * This method is used to find the token by token id.
	 * @param id:id of uuid type is the token id.
	 * @return token id
	 * 
	 */

	
	public Token findTokenById(UUID id) {
		LOGGER.info("Inside TokenService " + ": findTokenById()");
		Token token = tokenDao.getById(id);
		if (token == null) {
			throw new EntityNotFoundException();
		}
		else {
			return token;
		}
	}

}
