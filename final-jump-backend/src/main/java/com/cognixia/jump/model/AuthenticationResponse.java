package com.cognixia.jump.model;

// response for when accessing authentication endpoint for JWT
public class AuthenticationResponse {
	
	private final String jwt;
	private final String role;
	
	public AuthenticationResponse(String jwt, String role) {
		this.jwt = jwt;
		this.role = role;
	}

	public String getJwt() {
		return jwt;
	}
	
	public String getRole() {
		return role;
	}	
	

}
