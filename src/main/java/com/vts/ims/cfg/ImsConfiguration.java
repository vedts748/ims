package com.vts.ims.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//@Configuration
public class ImsConfiguration {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	 @Bean
	public PasswordEncoder passwordencoder(){
	     return new BCryptPasswordEncoder();
	    }
	
	
	   @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	        authProvider.setUserDetailsService(userDetailsService);
	        authProvider.setPasswordEncoder(passwordencoder());

	        return authProvider;
	    }
}