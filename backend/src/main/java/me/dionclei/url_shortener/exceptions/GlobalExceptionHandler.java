package me.dionclei.url_shortener.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UrlException.class)
	public ResponseEntity<StandardError> urlException(UrlException e, HttpServletRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		String error = "Url Exception";
	
		var err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status.value()).body(err);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFoundException(ResourceNotFoundException e, HttpServletRequest request) {
		var status = HttpStatus.NOT_FOUND;
		String error = "Resource not found";
	
		var err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status.value()).body(err);
	}
}
