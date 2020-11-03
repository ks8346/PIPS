package com.soprabanking.ips.dao;

import org.springframework.data.jpa.repository.JpaRepository;


import com.soprabanking.ips.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
