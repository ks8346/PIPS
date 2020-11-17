package com.soprabanking.ips.repositories;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.models.User;

/**
 * This is a repository interface
 * this interface extends the JPA repository which is used for fetching the user data from the database.
 * and in this interface, we fire some queries to fetch the data from the database
 * 
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * This method fetches the details of the user via username (email) by firing the custom query.
	 * @param email user email
	 */

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
    
    /**
	 * This method updates the password by firing the custom query.
	 * @param email, password  user email and password
	 */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.password= :password WHERE u.email= :email")
    public int updatePassword(@Param("email") String email, @Param("password") String password);

}
