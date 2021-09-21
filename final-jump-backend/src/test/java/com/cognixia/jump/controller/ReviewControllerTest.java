package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.cognixia.jump.exception.ResourceNotFoundException;
import com.cognixia.jump.model.Restaurant;
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.ReviewService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

private final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReviewService service;
	
	@InjectMocks
	private ReviewController controller;
	
	@Test
	void testGetAllReviews() throws Exception {
		
		String uri = STARTING_URI + "/review";
		
		User user1 = new User(1, "Devon", com.cognixia.jump.model.User.RoleType.CUSTOMER, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() );
		User user2 = new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() );
		
		Restaurant rest1 = new Restaurant(1, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() );
		Restaurant rest2 = new Restaurant(2, "Joe's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
		
		List<Review> allReviews = Arrays.asList(
					new Review(2, 4, "Great burgers. Must try!", LocalDate.of(2021, 9, 17), user1, rest1),
					new Review(3, 3, "Lobsters were good and delicious", LocalDate.of(2021, 9, 15), user2, rest1),
					new Review(4, 5, "Best steak ever!", LocalDate.of(2021, 9, 19), user1, rest1),
					new Review(5, 1, "Terrible service. Won't be eating here again.", LocalDate.of(2021, 9, 19), user2, rest1),
					new Review(6, 2, "Fries were good, but the rest was awful.", LocalDate.of(2021, 9, 20), user2, rest2)
				);
		
		when(service.getAllReviews()).thenReturn(allReviews);
		
		mockMvc.perform(get(uri))
				.andDo( print() )
				.andExpect(status().isOk());
		
		verify(service, times(1)).getAllReviews();
		verifyNoMoreInteractions(service);
		
	}
	
	// TODO -> issue with AssertionError
	@Test
	void testGetReviewById() throws Exception {
		
		int id = 5;
		String uri = STARTING_URI + "/review/{id}";
		
		User linkedUser = new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() );
		Restaurant linkedRestaurant = new Restaurant(1, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() );
		
		Review reviewFound = new Review(5, 1, "Terrible service. Won't be eating here again.", LocalDate.of(2021, 9, 19), linkedUser, linkedRestaurant);
		
		when(service.getReviewById(id)).thenReturn(reviewFound);
		
		mockMvc.perform( get(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath("$.id").value(reviewFound.getId()) )
				.andExpect( jsonPath("$.user").value(reviewFound.getUser()) )
				.andExpect( jsonPath("$.restaurant").value(reviewFound.getRestaurant()) )
				.andExpect( jsonPath("$.rating").value(reviewFound.getRating()) )
				.andExpect( jsonPath("$.comment").value(reviewFound.getComment()) )
				.andExpect( jsonPath("$.createdOn").value(reviewFound.getCreatedOn()) );
		
		verify(service, times(1)).getReviewById(id);
		verifyNoMoreInteractions(service);
	}
	
	 @Test
	 void testGetReviewNotFound() throws Exception {
	
		int id = 1000;
		String uri = STARTING_URI + "/review/{id}";
		
		when( service.getReviewById(id) )
			.thenThrow(new ResourceNotFoundException("Review with id = " + id + " was not found in DB."));

		mockMvc.perform( get(uri, id) )
		 		.andDo( print() )
		 		.andExpect( status().isNotFound() );
		
		verify(service, times(1)).getReviewById(id);
		verifyNoMoreInteractions(service);
			
	}
	
	@Test
	void testCreateReview() throws Exception {
		
		String uri = STARTING_URI + "/review";
		
		User linkedUser = new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() );
		Restaurant linkedRestaurant = new Restaurant(1, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() );
		
		Review reviewToCreate = new Review(100, 5, "Test review for user_2 & restaurant_1", LocalDate.of(2021, 10, 1), linkedUser, linkedRestaurant);

		 String reviewToCreateJSON = "{"
		 		+ "    \"user\": {"
		 		+ "        \"id\": " + reviewToCreate.getUser().getId()
		 		+ "    },"
		 		+ "    \"restaurant\": {"
		 		+ "        \"id\": " + reviewToCreate.getRestaurant().getId()
		 		+ "    },"
		 		+ "    \"rating\": " + reviewToCreate.getRating() + ","
		 		+ "    \"comment\": \"" + reviewToCreate.getComment() + "\","
		 		+ "    \"createdOn\": \"" + reviewToCreate.getCreatedOn() + "\""
		 		+ "}";
		
		when( service.addReview( Mockito.any(Review.class) )).thenReturn(reviewToCreate);
		
		mockMvc.perform( post(uri)
							.content(reviewToCreateJSON)
							.contentType(MediaType.APPLICATION_JSON_VALUE) )
		.andDo( print() )
		.andExpect( status().isCreated() )
		.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) );	
		
	}
	
	@Test
	void testGetReviewsOfUserId() throws Exception {
		
		// User ID to find their reviews
		int id = 2;
		String uri = STARTING_URI + "/review/user/{id}";
		
		User linkedUser = new User(id, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() );
		
		Restaurant rest1 = new Restaurant(1, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() );
		Restaurant rest2 = new Restaurant(2, "Joe's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
		
		List<Review> allReviewsOfUser2 = Arrays.asList(
				new Review(3, 3, "Lobsters were good and delicious", LocalDate.of(2021, 9, 15), linkedUser, rest1),
				new Review(5, 1, "Terrible service. Won't be eating here again.", LocalDate.of(2021, 9, 19), linkedUser, rest1),
				new Review(6, 2, "Fries were good, but the rest was awful.", LocalDate.of(2021, 9, 20), linkedUser, rest2),
				new Review(7, 5, "The pepsi was great!", LocalDate.of(2021, 9, 21), linkedUser, rest2)
			);
		
		when(service.getReviewsOfUser(id)).thenReturn(allReviewsOfUser2);
		
		mockMvc.perform(get(uri, id))
				.andDo( print() )
				.andExpect(status().isOk());
		
		
	}
	
	@Test
	void testGetReviewsOfRestaurantId() throws Exception {
		
		// Restaurant ID to find their reviews
		int id = 1;
		String uri = STARTING_URI + "/review/restaurant/{id}";
		
		Restaurant linkedRestaurant = new Restaurant(id, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() );
		
		User user1 = new User(1, "Devon", com.cognixia.jump.model.User.RoleType.CUSTOMER, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() );
		User user2 = new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() );
		
		List<Review> allReviewsOfRestaurant1 = Arrays.asList(
				new Review(2, 4, "Great burgers. Must try!", LocalDate.of(2021, 9, 17), user1, linkedRestaurant),
				new Review(3, 3, "Lobsters were good and delicious", LocalDate.of(2021, 9, 15), user2, linkedRestaurant),
				new Review(4, 5, "Best steak ever!", LocalDate.of(2021, 9, 19), user1, linkedRestaurant),
				new Review(5, 1, "Terrible service. Won't be eating here again.", LocalDate.of(2021, 9, 19), user2, linkedRestaurant)
			);
		
		when(service.getReviewsOfRestaurant(id)).thenReturn(allReviewsOfRestaurant1);
		
		mockMvc.perform(get(uri, id))
				.andDo( print() )
				.andExpect(status().isOk());
		
		
	}
	
}
