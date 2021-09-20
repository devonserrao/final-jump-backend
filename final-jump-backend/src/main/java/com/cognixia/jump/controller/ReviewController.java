package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
		
		Review review = service.getReviewById(id);
		
		if(review.getId() == -1)
			throw new ResourceNotFoundException("Review with id = " + id + " was not found in DB.");
		
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
		
		Review created = service.addReview(review);
		
		// if the returned Review's ID is -1, then either the Restaurant or User referenced don't exist
		if(created.getId() == -1) {
			// return an error
			throw new ResourceNotFoundException("Error creating Review. The specified User or Restaurant don't exist.\n" + review.toString());
		}
		// otherwise the operation was a success
		return ResponseEntity.status(201).body(created);
	}
	
	// get reviews of a user
	@GetMapping("/review/user/{id}")
	public ResponseEntity<?> getReviewsByUserId(@PathVariable int id) throws ResourceNotFoundException {
		List<Review> reviews = service.getReviewsOfUser(id);
		
		// if reviews is null, then the User does not exist
		if(reviews == null) {
			throw new ResourceNotFoundException("Error getting Reviews. The User with ID: " + id + " does not exist.");
		}
		// if reviews is empty, then the User has no reviews
		if(reviews.isEmpty()) {
			return ResponseEntity.status(204).body("The User with ID: " + id + " has no reviews.");
		}
		// the User was found, and has Reviews
		return ResponseEntity.status(200).body(reviews);
	}
	
	// get reviews of a restaurant
	@GetMapping("/review/restaurant/{id}")
	public ResponseEntity<?> getReviewsOfRestaurantId(@PathVariable int id) throws ResourceNotFoundException {
		List<Review> reviews = service.getReviewsOfRestaurant(id);
		
		// if reviews is null, then the Restaurant does not exist
		if(reviews == null) {
			throw new ResourceNotFoundException("Error getting Reviews. The Restaurant with ID: " + id + " does not exist.");
		}
		// if reviews is empty, then the Restaurant has no reviews
		if(reviews.isEmpty()) {
			return ResponseEntity.status(204).body("The Restaurant with ID: " + id + " has no reviews.");
		}
		// the Restaurant was found, and has Reviews
		return ResponseEntity.status(200).body(reviews);
	}
	
}
