package com.soprabanking.ips.services;

 

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

 

import com.soprabanking.ips.repositories.TokenRepository;

 /** 
  * This is service class
  * this class is used for deleting tokens once the session expires
  * 
  */

@Service
public class DeleteTokenService {
    
    @Autowired
    private TokenRepository token_repo;
    /**
     * this handler method is used for deleting tokens
     * @param token_id id of the token
     * 
     */
    
    public void deleteToken(UUID token_id) {
        
        if (token_repo.existsById(token_id)) {
            
            token_repo.deleteById(token_id);
        
        }
        
    }

 

}
 