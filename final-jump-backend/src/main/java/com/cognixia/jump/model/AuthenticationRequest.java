package com.cognixia.jump.model;

// what user will send in the /authenticate POST request to get back a JWT
public class AuthenticationRequest {
	
	private String username;
	private String password;
	
	public AuthenticationRequest() { }
	
	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
