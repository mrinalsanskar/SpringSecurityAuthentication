package com.cg.fms.springjwt.exception;

/*
 * Exception to handle if user already exits.
 * 
 * @author sanskar.
 * 
 */
public class UserExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserExistsException() {
		super();
	}

	public UserExistsException(String message) {
		super(message);
	}

}


