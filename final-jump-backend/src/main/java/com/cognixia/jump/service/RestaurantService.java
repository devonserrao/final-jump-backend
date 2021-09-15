package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.repository.RestaurantRepository;



@Service
public class RestaurantService {

	@Autowired
	RestaurantRepository repo;
	
	public List<Restaurant> getAllRestaurants() {
		return repo.findAll();
	}
	
	public Restaurant getRestaurantById(int id) {
		
		Optional<Restaurant> found = repo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		
		return new Restaurant();		
		
	}
	
	// ADMIN PRIVILEGE - PostMapping
	public Restaurant addRestaurant(Restaurant restaurant) {
		
		restaurant.setId(-1);
		
		Restaurant added = repo.save(restaurant);
		
		return added;
		
	}
	
	// ADMIN PRIVILEGE - PutMapping
	public Restaurant updateRestaurant(Restaurant restaurant) {
		
		Restaurant updated = new Restaurant();
		
		if( repo.existsById(restaurant.getId()) ) {
			updated = repo.save(restaurant);			
		}
		
		return updated;
		
	}
	
	// ADMIN PRIVILEGE - DeleteMapping
	public Restaurant deleteRestaurant(int id) {
		
		Restaurant deleted = new Restaurant();
		
		if(repo.existsById(id)) {
			
			deleted = repo.getById(id);
			repo.deleteById(id);
			
		}

		return deleted;
	}
	
}












