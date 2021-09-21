package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.RestaurantRepository;
import com.cognixia.jump.repository.ReviewRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository repo;
	
	@Autowired
	RestaurantRepository restaurantRepo;
	
	@Autowired
	UserRepository userRepo;
	
	public List<Review> getAllReviews() {
		return repo.findAll();
	}
	
	public Review getReviewById(int id) throws ResourceNotFoundException {
		
		Optional<Review> found = repo.findById(id);
		
		// if the Review does not exist, throw an exception
		if(!found.isPresent()) {
			throw new ResourceNotFoundException("Review with id = " + id + " was not found in DB.");
		}
		// otherwise, return the Review
		return found.get();
	}
	
	public Review addReview(Review review) throws ResourceNotFoundException {
		
		review.setId(-1);
		
		// check to make sure both the Restaurant and User exist
		int restaurantId = review.getRestaurant().getId();
		boolean restaurantExists = restaurantRepo.existsById(restaurantId);
		
		int userId = review.getUser().getId();
		boolean userExists = userRepo.existsById(userId);
		
		if(userExists && restaurantExists) {
			Review added = repo.save(review);
			return added;
		}
		// otherwise, throw an Exception
		throw new ResourceNotFoundException("Error creating Review. The specified User or Restaurant don't exist.\n" + review.toString());
	}
	
	public List<Review> getReviewsOfUser(int userId) throws ResourceNotFoundException {
		// find the User with the given ID
		Optional<User> user = userRepo.findById(userId);
		
		// if the User exists, return their reviews
		if(user.isPresent()) {
			return user.get().getReviews();
		}
		// the User does not exist, so throw an exception
		throw new ResourceNotFoundException("Error getting Reviews. The User with ID: " + userId + " does not exist.");
	}
	
	public List<Review> getReviewsOfRestaurant(int restaurantId) throws ResourceNotFoundException {
		// find the Restaurant with the given ID
		Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
		
		// if the Restaurant exists, return their reviews
		if(restaurant.isPresent()) {
			return restaurant.get().getReviews();
		}
		// the Restaurant does not exist, so throw an exception
		throw new ResourceNotFoundException("Error getting Reviews. The Restaurant with ID: " + restaurantId + " does not exist.");
	}
	
	
	
}
