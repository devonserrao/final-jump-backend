package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	public List<User> getAllUsers() {
		return repo.findAll();
	}
	
	public User getUserById(int id) {
		
		Optional<User> found = repo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		
		return new User();		
		
	}
	
	public User addUser(User user) {
		
		user.setId(-1);
		
		User added = repo.save(user);
		
		return added;
		
	}
}