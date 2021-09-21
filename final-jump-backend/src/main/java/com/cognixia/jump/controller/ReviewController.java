package com.cognixia.jump.controller;

import java.util.List;

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
import com.cognixia.jump.model.Review;
import com.cognixia.jump.service.ReviewService;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class ReviewController {

	@Autowired
	ReviewService service;
	
	@ApiOperation(value = "Get a list of all Reviews with restaurantId and userId")
	@GetMapping("/review")
	public List<Review> getAllReviews() {
		return service.getAllReviews();
	}

	@ApiOperation(value = "Get a Review by its ID",
			notes = "Pass in the Review's ID in the URL.")
	@GetMapping("/review/{id}")
	public Review getReviewById(@PathVariable int id) throws ResourceNotFoundException {
		
		Review review = null;
		
		try {
			review = service.getReviewById(id);
			
		} catch (ResourceNotFoundException e) {
			
			throw e;
		}
		
		return review;
	}
	
	// create review
	// the Restaurant and User must be passed in like this:
	/*
	 * {
	    	"user": {
	        	"id": 1
	    	},
	    	"restaurant": {
	        	"id": 1
	    	},
	    	"rating": 5,
	    	"comment": "Best steak ever!",
	    	"createdOn": "2021-09-19"
		}
	 */
	@ApiOperation(value = "Create a new Review.", 
			notes = "Pass in the Review's rating, comment (if available) and Date created on")
	@PostMapping("/review")
	public ResponseEntity<Review> createReview(@RequestBody Review review) throws ResourceNotFoundException {
		
		Review created = null;
		
		try {
			created = service.addReview(review);
		} catch (ResourceNotFoundException e) {
			// if an Exception was thrown, the Review was not properly created
			// propagate the exception so GlobalExceptionHandler can recognize it
			throw e;
		}
		
		// otherwise the operation was a success
		return ResponseEntity.status(201).body(created);
	}
	
	@ApiOperation(value = "Retrieve all reviews created by a User", 
			notes = "Pass in a User's ID in the URL")
	@GetMapping("/review/user/{id}")
	public ResponseEntity<?> getReviewsByUserId(@PathVariable int id) throws ResourceNotFoundException {
		List<Review> reviews = null;
		
		try {
			reviews = service.getReviewsOfUser(id);
		} catch (ResourceNotFoundException e) {
			// if no User was found, then this will catch the Exception
			// propagate it to the GlobalExceptionHandler
			throw e;
		}		
		
		// if reviews is empty, then the User has no reviews
		if(reviews.isEmpty()) {
			return ResponseEntity.status(204).body("The User with ID: " + id + " has no reviews.");
		}
		// the User was found, and has Reviews
		return ResponseEntity.status(200).body(reviews);
	}
	
	@ApiOperation(value = "Retrieve all reviews of a Restaurant", 
			notes = "Pass in a Restaurant's ID in the URL")
	@GetMapping("/review/restaurant/{id}")
	public ResponseEntity<?> getReviewsOfRestaurantId(@PathVariable int id) throws ResourceNotFoundException {
		List<Review> reviews = null;
		
		try {
			reviews = service.getReviewsOfRestaurant(id);
		} catch (ResourceNotFoundException e) {
			// if no Restaurant was found, then this will catch the Exception
			// the GlobalExceptionHandler will catch it and return a 404
			throw e;
		}
		
		
		// if reviews is empty, then the Restaurant has no reviews
		if(reviews.isEmpty()) {
			return ResponseEntity.status(204).body("The Restaurant with ID: " + id + " has no reviews.");
		}
		// the Restaurant was found, and has Reviews
		return ResponseEntity.status(200).body(reviews);
	}
	
}
