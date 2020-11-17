package com.soprabanking.ips.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.Token;
/**
 * This is a repository interface
 * this interface extends the JPA repository which is used for fetching the details of the token from token table
 * and in this interface, we fire some queries to fetch the data from the database
 * 
 */
public interface TokenRepository extends JpaRepository<Token, UUID> {
	/**
	 * This method fetches the details of the token by firing the custom query.
	 * @param email user email
	 */
	@Query("SELECT u FROM Token u WHERE u.email= :email")
    public Token getTokenByEmail(@Param("email") String email);
}
