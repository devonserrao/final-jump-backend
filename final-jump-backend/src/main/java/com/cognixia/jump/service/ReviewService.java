package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Review;
import com.cognixia.jump.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	ReviewRepository repo;
	
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
		
		Review added = repo.save(review);
		return added;
	}
	
	// TODO
//	public List<Review> getReviewsOfUser(int userId) {
//		
//	}
	
	// TODO
//	public List<Review> getReviewsOfRestaurants(int restaurantId) {
//		
//	}
	
	
	
}
