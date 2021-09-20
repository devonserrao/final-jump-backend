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
	@ApiOperation(value = "Create a new Review.", 
			notes = "Pass in the Review's rating, comment (if available) and Date created on")
	@PostMapping("/review")
	public ResponseEntity<Review> createReview(@RequestBody Review review) {
		
		Review created = service.addReview(review);
		return ResponseEntity.status(201).body(created);
		
	}
	
	// get reviews of a user
	
	// get reviews of a restaurant
	
}
