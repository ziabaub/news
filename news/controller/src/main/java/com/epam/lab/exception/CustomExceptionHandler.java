package com.epam.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
	private static final String INCORRECT_REQUEST = "INCORRECT_REQUEST";
	private static final String BAD_REQUEST = "BAD_REQUEST";
	private static final String UNAUTHORIZED_REQUEST = "UNAUTHORIZED REQUEST";


	@ExceptionHandler(NotAllowedDeletionException.class)
	public final ResponseEntity<ErrorResponse> handleDeletionException
			(NotAllowedDeletionException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(UNAUTHORIZED_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException
						(RecordNotFoundException ex, WebRequest request)
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MissingHeaderInfoException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
						(MissingHeaderInfoException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

}
