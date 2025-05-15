package me.dionclei.url_shortener.services.interfaces;

import me.dionclei.url_shortener.entities.User;

/**
 * This interface manages the token generation and validation
 */
public interface TokenService {
	
	/**
	 * 
	 * @param token the generated token to validate
	 * @return the user email used to generated the token as subject
	 */
	String validateToken(String token);
	
	
	/**
	 * 
	 * @param user the user to generate a token, the token subject must contain the user email
	 * @return a unique token
	 */
	String generateToken(User user);
}
