package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.MyUserPrincipal;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	UserService service;
	
	@ApiOperation(value = "Returns a list of all Users.")
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}
	
	@ApiOperation(value = "Get a User by their ID.", 
			notes = "Pass in the User's ID in the URL.")
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) throws ResourceNotFoundException {
		
		User user = null;
		try 
		{
			user = service.getUserById(id);
		} catch(ResourceNotFoundException e) {
			throw e; // Propogate the exception for GlobalExceptionHandler to recognize it.
		}
		
		return user;
	}
	
	@ApiOperation(value = "Create a new User.", 
			notes = "Pass in the User's name, username, password, and role.")
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		User added = service.addUser(user);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	// Logging in User
	@ApiOperation(value = "Login as a User.", 
			notes = "Pass in a username and password.")
	@GetMapping("/user/login")
	public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authRequest) throws Exception {
		
		// will catch the exception for bad credentials and...
		try {
			// make sure we can authenticate our user based on the username and password
			authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
					);
		} catch(BadCredentialsException e) {
			
			//...then provide own message as to why user could not be authenticated
			throw new Exception("Incorrect username or password");
		}
		
		// as long as user is found, we can create the JWT
		
		// find the user
		final MyUserPrincipal userDetails = myUserDetailsService.loadUserByUsername(authRequest.getUsername());
		//User userFound = service.getUserByUsername(authRequest.getUsername());
		
		// generate token for this user
		final String jwt = jwtUtil.generateTokens(userDetails, userDetails.getRole());
		
		// return token
		return ResponseEntity.status(200).body( new AuthenticationResponse(jwt) );
		
	}
	
}






