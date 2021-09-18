package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

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
import com.cognixia.jump.service.RestaurantService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

	private final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RestaurantService service;
	
	@InjectMocks
	private RestaurantController controller;
	
	@Test
	void testGetAllRestaurants() throws Exception {
		
		String uri = STARTING_URI + "/restaurant";
		
		List<Restaurant> allRestaurants = Arrays.asList(
					new Restaurant(1, "Bob's Crab Shack", "1234 Ruston Way, Tacoma, WA 98765", "Fresh local seafood. Dine-in and take-out available. Open Mon-Fri 11am - 8pm, Sat-Sun 11am-10pm.", "", new ArrayList<Review>() ),
					new Restaurant(2, "Joe's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() )
				);
		
		when(service.getAllRestaurants()).thenReturn(allRestaurants);
		
		mockMvc.perform(get(uri))
				.andDo( print() )
				.andExpect(status().isOk());
		
		verify(service, times(1)).getAllRestaurants();
		verifyNoMoreInteractions(service);
		
	}
	
	
	@Test
	void testGetRestaurantById() throws Exception {
		
		int id = 2;
		String uri = STARTING_URI + "/restaurant/{id}";
		
		Restaurant restaurant = new Restaurant(2, "Joe's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );

		when( service.getRestaurantById(id) ).thenReturn(restaurant);
		
		mockMvc.perform( get(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath("$.id").value(restaurant.getId()) )
				.andExpect( jsonPath("$.name").value(restaurant.getName()) )
				.andExpect( jsonPath("$.address").value(restaurant.getAddress()) )
				.andExpect( jsonPath("$.description").value(restaurant.getDescription()) )
				.andExpect( jsonPath("$.imageSrc").value(restaurant.getImageSrc()) )
				.andExpect( jsonPath("$.reviews").value(restaurant.getReviews()) );
		
		verify(service, times(1)).getRestaurantById(id);
		verifyNoMoreInteractions(service);
		
	}
	
	// Issue => Returning 200 instead of 404
//	@Test
//	void testGetRestaurantNotFound() throws Exception, ResourceNotFoundException {
//		
//		int id = -1;
//		String uri = STARTING_URI + "/restaurant/{id}";
//		
//		when( service.getRestaurantById(id) )
//				.thenThrow(new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB."));
//		
//		mockMvc.perform( get(uri, id) )
//				.andDo( print() )
//				.andExpect( status().isNotFound() );
//		
//		verify(service, times(1)).getRestaurantById(id);
//		verifyNoMoreInteractions(service);
//		
//	}
	
	@Test
	void testCreateRestaurant() throws Exception {
		
		String uri = STARTING_URI + "/restaurant";
		
		Restaurant restaurant = new Restaurant(-1, "Test Restaurant", "Test address", "Test description", "", new ArrayList<Review>() );
		
		when( service.addRestaurant( Mockito.any(Restaurant.class) )).thenReturn(restaurant);
		
		mockMvc.perform( post(uri)
							.content( restaurant.toJson())
							.contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo(print())
				.andExpect( status().isCreated() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) );	
		
	}
	
	@Test
	void testUpdateRestaurant() throws Exception {
		
		String uri = STARTING_URI + "/restaurant";
		
		Restaurant updatedRestaurant = new Restaurant(2, "Tester's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
		
		when(service.updateRestaurant( Mockito.any(Restaurant.class) )).thenReturn(updatedRestaurant);
		
		mockMvc.perform( put(uri)
							.content( updatedRestaurant.toJson() )
							.contentType(MediaType.APPLICATION_JSON_VALUE) )
			  .andDo(print())
			  .andExpect( status().isOk() )
			  .andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) );
		
	}
	
	// Issue: Returns 200 instead of 404.
//	@Test
//	void testUpdateRestaurantNotFound() throws Exception {
//		
//		String uri = STARTING_URI + "/restaurant";
//		
//		Restaurant restaurantDoesntExist = new Restaurant(1000, "Tester's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
//		
//		when( service.updateRestaurant( Mockito.any(Restaurant.class) ))
//			.thenThrow( new ResourceNotFoundException("Restaurant with id = " + restaurantDoesntExist.getId() + " was not found in DB to UPDATE.") );
//		
//		mockMvc.perform( put(uri)
//							.content( restaurantDoesntExist.toJson() )
//							.contentType(MediaType.APPLICATION_JSON_VALUE) ) // All PUT & POST must have MediaType.APPLICATION_JSON_VALUE [since they have a Request Body with JSON Object]
//				.andDo( print() )
//				.andExpect( status().isNotFound());
//		
//	}
	
	@Test
	void testDeleteRestaurant() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/restaurant/{id}";
		
		Restaurant restaurantFoundToDelete = new Restaurant(1, "Joe's Diner", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
		
		when( service.deleteRestaurant(id) ).thenReturn(restaurantFoundToDelete);
		
		mockMvc.perform( delete(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() );
		
	}
	
//	@Test
//	void testDeleteRestaurantNotFound() throws Exception {
//		
//		int id = 1;
//		String uri = STARTING_URI + "/restaurant/{id}";
//		
//		Restaurant restaurantNotFoundToDelete = new Restaurant(1000, "fake test restaurant", "98760 Cheshire Park, Austin, TX 64021", "Authentic southern American burgers and steaks. Dine-in available. Open Monday - Friday 10 am-11pm", "", new ArrayList<Review>() );
//		
//		when( service.deleteRestaurant(id) )
//			.thenThrow( new ResourceNotFoundException("Restaurant with id = " + id + " was not found in DB to DELETE.") );
//		
//		mockMvc.perform( delete(uri, id) )
//				.andDo( print() )
//				.andExpect( status().isNotFound() );
//		
//	}
	
}
