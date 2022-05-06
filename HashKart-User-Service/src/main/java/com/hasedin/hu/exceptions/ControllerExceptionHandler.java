package com.hasedin.hu.exceptions;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

	@Value(value = "${data.exception.message1}")
	private String message1;
	
	@Value(value = "${data.exception.message2}")
	private String message2;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				message1,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> resourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(),
				new Date(),
				message2,
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.ALREADY_REPORTED);
	}

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ErrorMessage> applicationException(ApplicationException ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				HttpStatus.BAD_REQUEST.value(),
				new Date(),
				ex.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}
}