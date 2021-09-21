package com.cognixia.jump.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognixia.jump.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("select u from User u where u.username = ?1 and u.password = ?2")
	User loginUser(String username, String password);
	
	// finds a User by username, for the UserDetailsService override method
	User findByUsername(String username);
}
