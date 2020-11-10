package com.soprabanking.ips.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.Token;

public interface TokenRepository extends JpaRepository<Token, UUID> {
	@Query("SELECT u FROM Token u WHERE u.email= :email")
    public Token getTokenByEmail(@Param("email") String email);
}
