package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class RestaurantController {

	@Autowired
	RestaurantService service;
	
	@ApiOperation(value = "Get a list of all Restaurants, with their reviews.")
	@GetMapping("/restaurant")
	public List<Restaurant> getAllRestaurants() {
		return service.getAllRestaurants();
	}
	
	@ApiOperation(value = "Get a Restaurant by its ID", 
			notes = "Pass in the Restaurant's ID in the URL.")
	@GetMapping("/restaurant/{id}")
	public Restaurant getRestaurantById(@PathVariable int id) throws ResourceNotFoundException {
		
		Restaurant restaurant = null;
		try 
		{
			restaurant = service.getRestaurantById(id);
			
		} catch(ResourceNotFoundException e) {
			throw e; // Propogate the exception for GlobalExceptionHandler to recognize it.
		}
		
		return restaurant;
	}
	
	// ADMIN ONLY - Add a restaurant
	@ApiOperation(value = "Create a new Restaurant.", 
			notes = "Pass in the Restaurant's name, address, description, and an image.")
	@PostMapping("/restaurant")
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
		
		Restaurant added = service.addRestaurant(restaurant);
		
		return ResponseEntity.status(201).body(added);
		
	}
	
	// ADMIN ONLY - Update a restaurant
	@ApiOperation(value = "Update a Restaurant's information.", 
			notes = "Pass in the Restaurant's new name, address, description, or image.")
	@PutMapping("/restaurant")
	public Restaurant updateRestaurant(@RequestBody Restaurant restaurant) throws ResourceNotFoundException {
		
		Restaurant updated = null;
		try {
		
			updated = service.updateRestaurant(restaurant);
		
		} catch(ResourceNotFoundException e) {
			throw e; // Propogate the exception for GlobalExceptionHandler to recognize it.
		}

		return updated;
		
	}
	
	// ADMIN ONLY - Delete a restaurant
	@ApiOperation(value = "Delete a Restaurant using its ID", 
			notes = "Pass in the Restaurant's ID in the URL.")
	@DeleteMapping("/restaurant/{id}")
	public Restaurant deleteRestaurant(@PathVariable int id) throws ResourceNotFoundException {
		
		Restaurant deleted = null;
		try {
			
			deleted = service.deleteRestaurant(id);	
			
		} catch(ResourceNotFoundException e) {
			throw e; // Propogate the exception for GlobalExceptionHandler to recognize it.
		}
		
		return deleted;		
	}	
}
