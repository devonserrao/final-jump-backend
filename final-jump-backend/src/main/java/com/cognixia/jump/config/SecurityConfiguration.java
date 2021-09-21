package com.cognixia.jump.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognixia.jump.filter.JwtRequestFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	// load in and authenticate the user (coming from the dummy service)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
	}
	
	// does password encoding (plain text)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	// authorization for all endpoints
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable() // disable csrf (common security attack)
			.authorizeRequests() 
			.antMatchers("/api/user/login").permitAll() // permit anyone with or w/o token to login
			.antMatchers(HttpMethod.GET, "/api/user/**").permitAll() // let anyone see a list of users, or a user by ID
			.antMatchers(HttpMethod.POST, "/api/user").permitAll() // anyone can make a new account
			.antMatchers(HttpMethod.GET, "/api/review/**").permitAll() // let anyone see all reviews, or reviews by user or restaurant
			.antMatchers(HttpMethod.GET, "/api/restaurant/**").permitAll() // let anyone see a list of restaurants
			.anyRequest().authenticated() // everything else will need a JWT token
			.and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // tell spring security not to create sessions b/c they
																	 // aren't needed with JWT
		
		// make sure to call filter for jwt on request before filter for checking username and password is used
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	// provides method for Spring Security to create and access the AuthenticationManager object needed
	// when authenticating users accessing our APIs (used in Autowire within HelloController class)
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	
	
}






