package com.cognixia.jump.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

@RequestMapping("/api")
@RestController
public class UserController {

	@Autowired
	UserService service;
	
	@GetMapping("/user")
	public List<User> getAllUsers() {
		return service.getAllUsers();
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) throws ResourceNotFoundException {
		User user = service.getUserById(id);
		
		if(user.getId() == -1) {
			throw new ResourceNotFoundException("User with id = " + id + " was not found in DB.");
		}
		
		return user;
	}
	
	
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		
		User added = service.addUser(user);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	// Logging in User
	@GetMapping("/user/login")
	public User loginUser(@RequestBody Map<String, String> loginCredentials) throws ResourceNotFoundException {
		
		// get username and password from the Map
		String inputUsername = loginCredentials.get("username");
		String inputPassword = loginCredentials.get("password");
		
		User loggedIn = service.checkUserInDb(inputUsername, inputPassword);
		
		if(loggedIn.getId() == -1) {
			throw new ResourceNotFoundException("No user with credentials [username = '" + inputUsername + "', password = '" + inputPassword + "'] found in DB!");
		}
		
		return loggedIn;
	}
	
}






