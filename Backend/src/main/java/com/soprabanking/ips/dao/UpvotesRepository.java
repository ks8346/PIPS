package com.soprabanking.ips.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.soprabanking.ips.entities.Upvotes;

public interface UpvotesRepository extends JpaRepository<Upvotes, Long> {

}
