package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.User;
import com.cognixia.jump.model.User.RoleType;
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
	
	public User getUserByUsername(String username) throws ResourceNotFoundException {
		User found = repo.findByUsername(username);
		
		if(found != null) {
			return found;
		}
		
		throw new ResourceNotFoundException("User with username = " + username + " was not found in DB.");	
	}
	
	public User addCustomer(User user) throws IllegalArgumentException {
		
		user.setId(-1);
		user.setRole(RoleType.CUSTOMER);
		
		User added = repo.save(user);
		
		if(added != null) {
			return added;
		}
		
		throw new IllegalArgumentException("Failed to create new Customer account. Username " + user.getUsername() + " already in use.");
		
	}
	
	public User addAdmin(User user) throws IllegalArgumentException {
		
		user.setId(-1);
		user.setRole(RoleType.ADMIN);
		
		User added = repo.save(user);
		
		if(added != null) {
			return added;
		}
		
		throw new IllegalArgumentException("Failed to create new Admin account. Username " + user.getUsername() + " already in use.");		
	}
	
	public User loginUser(String username, String password) throws ResourceNotFoundException {
		User found = repo.loginUser(username, password);
		
		if(found != null) {
			return found;
		}
		
		throw new ResourceNotFoundException("No user with credentials [username = '" + username + "', password = '" + password + "'] found in DB!");
	}
	
}