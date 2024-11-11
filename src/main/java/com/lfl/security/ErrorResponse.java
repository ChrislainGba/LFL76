package com.lfl.security;

public class ErrorResponse {
	private final String message;
	private final String description;

	public ErrorResponse(String description, String message) {
	    this.message = message;
	    this.description = description;
	}

	public String getMessage() {
	    return message;
	}

	public String getDescription() {
	    return description;
	}
}
