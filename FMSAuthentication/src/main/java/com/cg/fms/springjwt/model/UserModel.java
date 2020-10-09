package com.cg.fms.springjwt.model;

import javax.validation.constraints.NotNull;

/*
 * This class creates model class and is responsible for getting values from user 
 * and passing it to the DAO layer for inserting in database. 
 * 
 * Models work on DTO layer.
 * DTO(Data Transfer Object) is used to transfer the data between classes and modules of your application. 
 * DTO should only contain private fields for your data, getters, setters and constructors. 
 * It is not recommended to add business logic methods to such classes, but it is OK to add some util methods.
 * It's basically a value object used for passing structured data between tiers / layers.
 * 
 * author sanskar.
 * 
 */
public class UserModel {
	
	@NotNull
	private String firstName;
	
	private String lastName;
	
	@NotNull
	private String email;
	
	private String mobile;
	
	@NotNull
	private String username;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

