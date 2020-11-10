package com.soprabanking.ips.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soprabanking.ips.models.Token;

public interface TokenRepository extends JpaRepository<Token, UUID> {
	
}
