package me.dionclei.url_shortener.services.interfaces;

import me.dionclei.url_shortener.dto.GenericPage;
import me.dionclei.url_shortener.entities.Url;

/**
 * Service for managing shortened URLs.
 */
public interface UrlService {
	
	/**
	 * Retrieves a Url entity based on its shortened URL.
	 * 
	 * @param url the shortened URL
	 * @return the Url entity if found, otherwise null
	 */
	Url findByShortenedUrl(String url);
	
	/**
	 * Generates a new Url entity from the original URL.
	 * It sets creation and expiration dates, saves it to generate an ID,
	 * and then uses that ID to generate a unique shortened URL.
	 * 
	 * @param originalUrl the original long URL
	 * @param userEmail the email that the URL will be added to
	 * @return the saved Url entity with a shortened URL
	 */
	Url generateUrl(String originalUrl, String userEmail);
	
	/**
	 * Retrieves a genericPage from all URLs that
	 * a user created
	 * 
	 * @param userEmail the user's email
	 * @param page the current page
	 * @return a generic page with all URLs
	 */
	GenericPage<Url> getAllUrls(String userEmail, Integer page);
	
	/**
	 * This method clears all expired URLs
	 */
	void clean();
}
