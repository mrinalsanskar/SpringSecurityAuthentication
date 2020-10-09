package com.cg.fms.springjwt.payload.response;

/*
 * This class defines bean for a message that would be displayed when needed.
 * 
 * author sanskar.
 * 
 */
public class MessageResponse {
	private String message;

	public MessageResponse(String message) {
	    this.message = message;
	  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
