package me.dionclei.url_shortener.exceptions;

public class UrlException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UrlException(String message) {
		super(message);
	}
}
