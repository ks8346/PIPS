package com.soprabanking.ips.dao;

import java.util.List;


//import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.soprabanking.ips.entities.User;


public interface UserRepository extends JpaRepository<User, Long>  {
	
	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);
	
	//public List<User> findByName(String name);
	//public User getUserByUserName(@Param("email") String email); 
	
	//@Query("SELECT u FROM User u ")

	//public List<User> getAlluser();
		
//@Query("SELECT u.id,u.name FROM User u ")
//List<Object> getidANDname();





//public List<User> getAlluser();





	
   //  @Query(value="select * from users  ", nativeQuery=true)
    // public List<User> getUsers();

}
