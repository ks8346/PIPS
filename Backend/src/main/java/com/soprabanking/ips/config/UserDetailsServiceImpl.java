package com.soprabanking.ips.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.soprabanking.ips.controllers.ForgotPasswordController;
import com.soprabanking.ips.models.User;
import com.soprabanking.ips.repositories.UserRepository;
/**
 * User Details Service Impl
 * This class implements UserDetailsService interface of spring boot security.
 * Core interface which loads user-specific data.
 * <p>
 * It is used throughout the framework as a user DAO and is the strategy used by the
 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
 * DaoAuthenticationProvider}.
 *
 * <p>
 * The interface requires only one read-only method, which simplifies support for new
 * data-access strategies.
 * @author kavsharma
 */

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private static final Logger LOGGER = LogManager.getLogger( UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    /**
	 * Locates the user based on the username. In the actual implementation, the search
	 * may possibly be case sensitive, or case insensitive depending on how the
	 * implementation instance is configured. In this case, the <code>UserDetails</code>
	 * object that comes back may have a username that is of a different case than what
	 * was actually requested..
	 *
	 * @param username the username identifying the user whose data is required.
	 *
	 * @return a fully populated user record (never <code>null</code>)
	 *
	 * @throws UsernameNotFoundException if the user could not be found or the user has no
	 * GrantedAuthority
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // fetching user from database
    	LOGGER.info("Inside  UserDetailsServiceImpl: loadUserByUsername() method");
    
        User user = userRepository.getUserByUserName(username);

        if (user == null) {
        	LOGGER.error("Inside  UserDetailsServiceImpl :loadUserByUsername() FAILURE");
            throw new UsernameNotFoundException("Could not found user !!");
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        LOGGER.info("Inside  UserDetailsServiceImpl : loadUserByUsername() SUCCESS");
        return customUserDetails;
    }

}
