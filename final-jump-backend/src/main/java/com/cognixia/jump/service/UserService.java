package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public User getUserById(int id) throws ResourceNotFoundException {
		
		Optional<User> found = repo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		
		throw new ResourceNotFoundException("User with id = " + id + " was not found in DB.");	
		
	}
	
	public User addUser(User user) {
		
		user.setId(-1);
		
		User added = repo.save(user);
		
		return added;
		
	}
	
	public User loginUser(String username, String password) throws ResourceNotFoundException {
		User found = repo.loginUser(username, password);
		
		if(found != null) {
			return found;
		}
		
		throw new ResourceNotFoundException("No user with credentials [username = '" + username + "', password = '" + password + "'] found in DB!");
	}
}