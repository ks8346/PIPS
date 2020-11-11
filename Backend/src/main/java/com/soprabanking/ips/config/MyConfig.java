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

 

@Configuration
@EnableWebSecurity
@Transactional
//@EnableOAuth2Sso
public class MyConfig extends WebSecurityConfigurerAdapter {

 

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();
    }
    @Bean     
    public ReentrantLock getLock() {         
    	return new ReentrantLock();     
    	}
 

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

 

        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

 

        return daoAuthenticationProvider;

 

    }

 

    /// configure method...

 

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());

 

    }

 

    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/user/**").hasRole("USER")
        //.antMatchers("/signin").hasRole("USER")
        .antMatchers("/**").permitAll().anyRequest()
               
                .authenticated().and()
                .formLogin().and()
                .httpBasic()
                //.defaultSuccessUrl("/user/index")
                //.loginPage("/signin").defaultSuccessUrl("/user/index")
                .and().oauth2Login()
                ;
    }

 


}