package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;

enum RoleType {
	CUSTOMER, ADMIN
}

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	private final String STARTING_URI = "http://localhost:8080/api";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService service;
	
	@InjectMocks
	private UserController controller;
	
	@Test
	void testGetAllUsers() throws Exception {
	
		String uri = STARTING_URI + "/user";
		
		// TODO -> enum RoleType issue
		List<User> allUsers = Arrays.asList(
//					new User(1, "Devon", com.cognixia.jump.model.User.RoleType.CUSTOMER, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() ),
//					new User(2, "Nicholas", TODO, "nc@gmail.com", "rootroot", new ArrayList<Review>() )
				);
		
		when(service.getAllUsers()).thenReturn(allUsers);
		
		mockMvc.perform(get(uri))
				.andDo( print() )
				.andExpect(status().isOk());
		
		verify(service, times(1)).getAllUsers();
		verifyNoMoreInteractions(service);
		
	}
	
//	@Test
//	void testGetUserById() throws Exception {

//		int id = 2;
//		String uri = STARTING_URI + "/user/{id}";
//		
//		User userFound = new User(1, "Devon", TODO, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() );
//	
//		when( service.getUserById(id) ).thenReturn(userFound);
//		
//		mockMvc.perform( get(uri, id) )
//				.andDo( print() )
//				.andExpect( status().isOk() )
//				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
//				.andExpect( jsonPath("$.id").value(userFound.getId()) )
//				.andExpect( jsonPath("$.name").value(userFound.getName()) )
//				.andExpect( jsonPath("$.role").value(userFound.getRole()) )
//				.andExpect( jsonPath("$.username").value(userFound.getUsername()) )
//				.andExpect( jsonPath("$.password").value(userFound.getPassword()) )
//				.andExpect( jsonPath("$.reviews").value(userFound.getReviews()) );
//		
//		verify(service, times(1)).getUserById(id);
//		verifyNoMoreInteractions(service);
	
//	}

//	@Test
//	void testCreateUser() throws Exception {
//
//		String uri = STARTING_URI + "/restaurant";
//		
//		User userToCreate = new User(-1, "Test User", TODO, "test username", "test password", new ArrayList<Review>());
//		
//		when( service.addUser( Mockito.any(User.class) )).thenReturn(userToCreate);
//		
//		mockMvc.perform( post(uri)
//							.content( userToCreate.toJson())
//							.contentType(MediaType.APPLICATION_JSON_VALUE) )
//				.andDo( print() )
//				.andExpect( status().isCreated() )
//				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) );	
//	
//	}

//	@Test
//	void testLoginUser() throws Exception {
	
//	}
	
// testLoginUserFailed()
// testGetUserNotFound()
	
	
	
}
