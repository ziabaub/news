package com.epam.lab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotAllowedDeletionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public NotAllowedDeletionException(String message) {
        super(message);
    }
}
