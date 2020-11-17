package com.soprabanking.ips.config;

 

import java.util.Timer;
import java.util.concurrent.locks.ReentrantLock;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soprabanking.ips.controllers.ForgotPasswordController;
/**
 * MyConFig Class
 * This is a configuration class of spring boot security.
 * 
 *
 * <p>
 * this is configuration class which implements spring boot security.Basically in this class, 
 * we override the methods of spring boot security and also implement DAO authentication and 
 * URL protection from unwanted actions.
 * this configuration class extends WebSecurityConfigureAdapter.
 * To enable authentication and authorization support in spring boot rest APIs, 
 * we cconfigure a utility class WebSecurityConfigurerAdapter. 
 * It helps in requiring the user to be authenticated prior to accessing any configured URL (or all URLs) within our application.
 * 
 * 
 */
 

@Configuration
@EnableWebSecurity
@Transactional
public class MyConfig extends WebSecurityConfigurerAdapter {
	private static final Logger LOGGER = LogManager.getLogger(MyConfig.class);

 /**
  * this method calls userDetailService class 
  * @return UserDetailsServiceImpl object of UserdetailsServiceImpl
  */

    @Bean
    public UserDetailsService getUserDetailService() {
    	LOGGER.info("Inside  MyConfig : getUserDetailService() method");
        return new UserDetailsServiceImpl();
    }
    /**
     * this method calls ReentrantLock class 
     * @return ReentrantLock object of ReentrantLock
     */
    @Bean     
    public ReentrantLock getLock() {     
    	LOGGER.info("Inside  MyConfig : getLock() method");
    	return new ReentrantLock();     
    	}
    
    /**
     * this method calls ObjectMapper class 
     * @return ObjectMapper object of ObjectMapper
     */
    @Bean     
    public ObjectMapper getMapper() {     
    	LOGGER.info("Inside  MyConfig : getMapper() method");
    	return new ObjectMapper();     
    	}

    /**
     * this method calls Timer class 
     * @return Timer object of Timer
     */
    @Bean     
    public Timer getTimer() {     
    	LOGGER.info("Inside  MyConfig : getTimer() method");
    	return new Timer();     
    	}
    /**
     * this method calls BCryptPasswordEncoder class to encrypt the password of user.
     * @return BcryptPasswordEncoder object of BcryptPasswordEncoder
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
    	LOGGER.info("Inside  MyConfig :  passwordEncoder() method");
        return new BCryptPasswordEncoder();
    }

    /**
     * this method implements DaoAuthentication to authenticate the user via database.
     * @return daoAuthenticationProvider object of daoAuthenticationProvider.
     */

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
    	LOGGER.info("Inside  MyConfig :authenticationProvider() method");
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

 

        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

 

        return daoAuthenticationProvider;

 

    }

 

    /// configure method...

    /**
     * AuthenticationManagerBuilder is a helper class that eases the set up of UserDetailService,
     * AuthenticationProvider, and other dependencies to build an AuthenticationManager.
     * @param auth object of AuthenticationManagerBuilder
     * @throws Exception throw an AuthenticationException if it believes that the input represents an invalid principal.
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	LOGGER.info("Inside MyConfig : configure() method");
        auth.authenticationProvider(authenticationProvider());

 

    }

    /**
     * This method implements the web security.
     * In this method, we protect those URLs which are to be shown to only authenticated users.
     * @param http object of HttpSecurity
     * @throws Exception throw an Exception when unauthorized person performs unwanted actions on our site.
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	LOGGER.info("Inside  MyConfig: configure() method");
        http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/**").permitAll().anyRequest()
               
                .authenticated().and()
                .formLogin().and()
                .httpBasic()
                .and().oauth2Login()
                ;
    }

 


}