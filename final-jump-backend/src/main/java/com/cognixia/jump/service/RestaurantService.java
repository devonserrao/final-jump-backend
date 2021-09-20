package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.repository.RestaurantRepository;



@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository repo;
	
	public List<Restaurant> getAllRestaurants() {
		return repo.findAll();
	}
	
	public Restaurant getRestaurantById(int id) throws ResourceNotFoundException {
		
		Optional<Restaurant> found = repo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		
		throw new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB.");	
		
	}
	
	// ADMIN PRIVILEGE - PostMapping
	public Restaurant addRestaurant(Restaurant restaurant) {
		
		restaurant.setId(-1);
		
		Restaurant added = repo.save(restaurant);
		
		return added;
		
	}
	
	// ADMIN PRIVILEGE - PutMapping
	public Restaurant updateRestaurant(Restaurant restaurant) throws ResourceNotFoundException {
		
		Restaurant updated = new Restaurant();
		
		if( repo.existsById(restaurant.getId()) ) {
			updated = repo.save(restaurant);	
			return updated;
		}
		
		throw new ResourceNotFoundException("Restaurant with id = " + restaurant.getId() + " was not found in DB to UPDATE.");
		
	}
	
	// ADMIN PRIVILEGE - DeleteMapping
	public Restaurant deleteRestaurant(int id) throws ResourceNotFoundException {
		
		Restaurant deleted = new Restaurant();
		
		if(repo.existsById(id)) {
			
			deleted = repo.getById(id);
			repo.deleteById(id);
			return deleted;
			
		}

		throw new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB to DELETE.");
	}
	
}












