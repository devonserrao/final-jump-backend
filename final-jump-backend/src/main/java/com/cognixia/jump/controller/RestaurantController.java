package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.service.RestaurantService;

@RequestMapping("/api")
@RestController
public class RestaurantController {

	@Autowired
	RestaurantService service;
	
	@GetMapping("/restaurant")
	public List<Restaurant> getAllRestaurants() {
		return service.getAllRestaurants();
	}
	
	@GetMapping("/restaurant/{id}")
	public Restaurant getRestaurantById(@PathVariable int id) throws ResourceNotFoundException {
		
		Restaurant restaurant = service.getRestaurantById(id);
		
		if(restaurant.getId() == -1) { 
			throw new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB.");
		}
		
		return restaurant;
	}
	
	// ADMIN ONLY - Add a restaurant
	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
		
		Restaurant added = service.addRestaurant(restaurant);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	// ADMIN ONLY - Update a restaurant
	@PutMapping("/restaurant")
	public Restaurant updateRestaurant(@RequestBody Restaurant restaurant) throws ResourceNotFoundException {
		
		Restaurant updated = service.updateRestaurant(restaurant);
		
		if(updated.getId() == -1) {
			throw new ResourceNotFoundException("Restaurant with id = " + restaurant.getId() + " was not found in DB to UPDATE.");
		}

		return updated;
		
	}
	
	// ADMIN ONLY - Delete a restaurant
	@DeleteMapping("/restaurant/{id}")
	public Restaurant deleteRestaurant(@PathVariable int id) throws ResourceNotFoundException {
		
		Restaurant deleted = service.deleteRestaurant(id);
		
		if(deleted.getId() == -1) {
			throw new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB to DELETE.");
		}
		
		return deleted;
		
	}
	
	
	
}
