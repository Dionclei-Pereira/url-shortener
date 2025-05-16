package me.dionclei.url_shortener.services.interfaces;

import java.util.Optional;

import me.dionclei.url_shortener.entities.User;

/**
 * The service to manage users of the application
 */
public interface UserService {
	/**
	 * Retrieves a user by email
	 * 
	 * @param email the email of the user
	 * @return a user
	 */
	Optional<User> findByEmail(String email);
	
}
