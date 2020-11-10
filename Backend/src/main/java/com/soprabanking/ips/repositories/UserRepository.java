package com.soprabanking.ips.repositories;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
    
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password= :password WHERE u.email= :email")
    public void updatePassword(@Param("email") String email, @Param("password") String password);


}
