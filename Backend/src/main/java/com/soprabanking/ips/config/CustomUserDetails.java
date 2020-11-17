package com.soprabanking.ips.config;

import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.soprabanking.ips.controllers.ForgotPasswordController;
import com.soprabanking.ips.models.User;
/**
 * Custom User Details
 * This class implements userDetails interface of spring boot security.
 * Provides core user information.
 *
 * <p>
 * Implementations are not used directly by Spring Security for security purposes. They
 * simply store user information which is later encapsulated into {@link Authentication}
 * objects. This allows non-security related user information (such as email addresses,
 * telephone numbers etc) to be stored in a convenient location.
 * <p>
 * Concrete implementations must take particular care to ensure the non-null contract
 * detailed for each method is enforced. See
 * {@link org.springframework.security.core.userdetails.User} for a reference
 * implementation (which you might like to extend or use in your code).
 * 
 */

public class CustomUserDetails implements UserDetails {
	
	private static final Logger LOGGER = LogManager.getLogger(CustomUserDetails.class);
    private User user;

    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

	/**
	 * Returns the authorities granted to the user. Cannot return <code>null</code>.
	 *
	 * @return the authorities, sorted by natural key (never <code>null</code>)
	 */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	LOGGER.info("Inside CustomUserDetails :getAuthorities() method");

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
        return List.of(simpleGrantedAuthority);
    }
    /**
	 * Returns the password used to authenticate the user.
	 *
	 * @return the password
	 */

    @Override
    public String getPassword() {
    	LOGGER.info("Inside CustomUserDetails : getPassword() method");
        return user.getPassword();
    }
    /**
	 * Returns the username used to authenticate the user. Cannot return <code>null</code>.
	 *
	 * @return the username (never <code>null</code>)
	 */
    @Override
    public String getUsername() {
    	LOGGER.info("Inside CustomUserDetails : getUsername() method");

        return user.getEmail();
    }
    /**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
    @Override
    public boolean isAccountNonExpired() {
    	LOGGER.info("Inside CustomUserDetails : isAccountNonExpired() method");

        return true;
    }
    /**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
	 */

    @Override
    public boolean isAccountNonLocked() {
    	LOGGER.info("Inside CustomUserDetails : isAccountNonLocked() method");

        return true;
    }
    /**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 *
	 * @return <code>true</code> if the user's credentials are valid (ie non-expired),
	 * <code>false</code> if no longer valid (ie expired)
	 */
    @Override
    public boolean isCredentialsNonExpired() {
    	LOGGER.info("Inside CustomUserDetails :isCredentialsNonExpired() method");

        return true;
    }
    /**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */

    @Override
    public boolean isEnabled() {
    	LOGGER.info("Inside CustomUserDetails : isEnabled() method");

        return true;
    }

    @Override
    public String toString() {
        return "CustomUserDetails [user=" + user + "]";
    }


}
