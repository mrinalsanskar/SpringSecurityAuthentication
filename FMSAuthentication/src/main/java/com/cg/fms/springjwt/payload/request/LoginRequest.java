package com.cg.fms.springjwt.payload.request;

import javax.validation.constraints.NotBlank;

/*
 * This class is required for storing the username and password we receive from the client.
 * These values go as a request when trying to login.
 * 
 * author sanskar.
 * 
 */
public class LoginRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

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
