package com.epam.lab.exception;

import java.util.List;

public class ErrorResponse {
	ErrorResponse(String message, List<String> details) {
		this.message = message;
		this.details = details;
	}

	private String message;
	private List<String> details;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
}
