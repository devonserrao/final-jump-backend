package com.cognixia.jump.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cognixia.jump.model.Review;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.UserService;
import com.google.gson.Gson;

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
		
		List<User> allUsers = Arrays.asList(
					new User(1, "Devon", com.cognixia.jump.model.User.RoleType.CUSTOMER, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() ),
					new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>() )
				);
		
		when(service.getAllUsers()).thenReturn(allUsers);
		
		mockMvc.perform(get(uri))
				.andDo( print() )
				.andExpect(status().isOk());
		
		verify(service, times(1)).getAllUsers();
		verifyNoMoreInteractions(service);
		
	}
	
	@Test
	void testGetUserById() throws Exception {

		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		User userFound = new User(1, "Devon", com.cognixia.jump.model.User.RoleType.CUSTOMER, "devon123@gmail.com", "devtestpass", new ArrayList<Review>() );
	
		when( service.getUserById(id) ).thenReturn(userFound);
		
		mockMvc.perform( get(uri, id) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath("$.id").value(userFound.getId()) )
				.andExpect( jsonPath("$.name").value(userFound.getName()) )
				.andExpect( jsonPath("$.role").value(userFound.getRole().toString()) )
				.andExpect( jsonPath("$.username").value(userFound.getUsername()) )
				.andExpect( jsonPath("$.password").value(userFound.getPassword()) )
				.andExpect( jsonPath("$.reviews").value(userFound.getReviews()) );
		
		verify(service, times(1)).getUserById(id);
		verifyNoMoreInteractions(service);
	
	}
	
	@Test
	void testGetUserNotFound() throws Exception {
		
		int id = 1;
		String uri = STARTING_URI + "/user/{id}";
		
		when( service.getUserById(id) )
			.thenThrow(new ResourceNotFoundException("User with id = " + id + " was not found in DB."));
		
		mockMvc.perform( get(uri, id) )
				.andDo( print() )
				.andExpect( status().isNotFound() );
		
		verify(service, times(1)).getUserById(id);
		verifyNoMoreInteractions(service);
	}

	@Test
	void testCreateUser() throws Exception {

		String uri = STARTING_URI + "/user";
		
		User userToCreate = new User(5, "Test customer", com.cognixia.jump.model.User.RoleType.CUSTOMER, "test username", "test password", new ArrayList<Review>());
		
		when( service.addUser( Mockito.any(User.class) )).thenReturn(userToCreate);
		
		mockMvc.perform( post(uri)
							.content( userToCreate.toJson())
							.contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo( print() )
				.andExpect( status().isCreated() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) );	
	
	}

	// returning 500 instead of 200
	@Test
	void testLoginUser() throws Exception {
	
		String uri = STARTING_URI + "/user/login";
		
		User userExpectedToLogin = new User(2, "Nicholas", com.cognixia.jump.model.User.RoleType.ADMIN, "nc@gmail.com", "rootroot", new ArrayList<Review>());
		
		//String json = "{\"username\": \"nc@gmail.com\", \"password\" : \"rootroot\"}";
		
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("username", "nc@gmail.com");
		credentials.put("password", "rootroot");
		
		Gson gson = new Gson();		
		
		when( service.loginUser("nc@gmail.com", "rootroot") ).thenReturn(userExpectedToLogin);
		
		mockMvc.perform( get(uri)
							.content(gson.toJson(credentials))
							.contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andDo( print() )
				.andExpect( status().isOk() )
				.andExpect( content().contentType(MediaType.APPLICATION_JSON_VALUE) )
				.andExpect( jsonPath("$.id").value(userExpectedToLogin.getId()) )
				.andExpect( jsonPath("$.name").value(userExpectedToLogin.getName()) )
				.andExpect( jsonPath("$.role").value(userExpectedToLogin.getRole().toString()) )
				.andExpect( jsonPath("$.username").value(userExpectedToLogin.getUsername()) )
				.andExpect( jsonPath("$.password").value(userExpectedToLogin.getPassword()) )
				.andExpect( jsonPath("$.reviews").value(userExpectedToLogin.getReviews()) );
	}
	
	@Test
	void testLoginUserFail() throws Exception {
		
		String uri = STARTING_URI + "/user/login";
		
		// login with bad credentials
		String username = "nc@gmail.com";
		String password = "rootroo";
		
		Map<String, String> badCredentials = new HashMap<String, String>();
		badCredentials.put("username", username);
		badCredentials.put("password", password); // this password is incorrect
		
		Gson gson = new Gson();
		
		when( service.loginUser(username, password) )
			.thenThrow(new ResourceNotFoundException("No user with credentials [username = 'nc@gmail.com', password = 'rootroo'] found in DB!"));
	
		mockMvc.perform( get(uri)
				.content(gson.toJson(badCredentials))
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo( print() )
				.andExpect( status().isNotFound() );
		
		verify(service, times(1)).loginUser(username, password);
		verifyNoMoreInteractions(service);
		
	}	
	
}
