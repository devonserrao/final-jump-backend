package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

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
			notes = "Pass in the User's name, password, and role.")
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		User added = service.addUser(user);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	// Logging in User
	@ApiOperation(value = "Login as a User.", 
			notes = "Pass in a username and password.")
	@GetMapping("/user/login")
	public User loginUser(@RequestBody Map<String, String> loginCredentials) throws ResourceNotFoundException {
		
		// get username and password from the Map
		String inputUsername = loginCredentials.get("username");
		String inputPassword = loginCredentials.get("password");
		
		User loggedIn = null;
		try 
		{
			loggedIn = service.loginUser(inputUsername, inputPassword);
		} catch(ResourceNotFoundException e) {
			throw e; // Propogate the exception for GlobalExceptionHandler to recognize it.
		}

		
		return loggedIn;
	}
	
}






