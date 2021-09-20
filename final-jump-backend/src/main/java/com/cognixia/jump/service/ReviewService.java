package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public Review getReviewById(int id) {
		
		Optional<Review> found = repo.findById(id);
		
		if(found.isPresent()) {
			return found.get();
		}
		
		return new Review();
		
	}
	
	public Review addReview(Review review) {
		
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
		return new Review();
	}
	
	public List<Review> getReviewsOfUser(int userId) {
		// find the User with the given ID
		Optional<User> user = userRepo.findById(userId);
		
		// if the User exists, return their reviews
		if(user.isPresent()) {
			return user.get().getReviews();
		}
		// the User does not exist, so return null
		return null;
	}
	
	public List<Review> getReviewsOfRestaurant(int restaurantId) {
		// find the Restaurant with the given ID
		Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
		
		// if the Restaurant exists, return their reviews
		if(restaurant.isPresent()) {
			return restaurant.get().getReviews();
		}
		// the Restaurant does not exist, so return null
		return null;
	}
	
	
	
}
