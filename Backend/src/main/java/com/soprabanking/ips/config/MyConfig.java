package com.soprabanking.ips.config;

 

import java.util.concurrent.locks.ReentrantLock;

import javax.transaction.Transactional;

 

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
/**
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

 /**
  * this method calls userDetailService class 
  * @return UserDetailsServiceImpl object of UserdetailsServiceImpl
  */

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();
    }
    /**
     * this method calls ReentrantLock class 
     * @return ReentrantLock object of ReentrantLock
     */
    @Bean     
    public ReentrantLock getLock() {         
    	return new ReentrantLock();     
    	}
    /**
     * this method calls BCryptPasswordEncoder class to encrypt the password of user.
     * @return BcryptPasswordEncoder object of BcryptPasswordEncoder
     */

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * this method implements DaoAuthentication to authenticate the user via database.
     * @return daoAuthenticationProvider object of daoAuthenticationProvider.
     */

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
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