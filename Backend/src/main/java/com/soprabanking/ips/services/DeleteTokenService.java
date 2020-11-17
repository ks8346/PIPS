package com.soprabanking.ips.services;

 

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.soprabanking.ips.repositories.TokenRepository;

 /** 
  * Delete Token Service
  * This is service class
  * this class is used for deleting tokens once the session expires
  * @author kavsharma
  * 
  */

@Service
public class DeleteTokenService {

	 private static final Logger LOGGER = LogManager.getLogger(DeleteTokenService.class);
    
    @Autowired
    private TokenRepository token_repo;
    /**
     * this handler method is used for deleting tokens
     * @param token_id id of the token
     * 
     */
    
    public void deleteToken(UUID token_id) {
    	LOGGER.info("Inside DeleteTokenService : deleteToken() method");
        
        if (token_repo.existsById(token_id)) {
            
            token_repo.deleteById(token_id);
        
        }
        
    }

 

}
 