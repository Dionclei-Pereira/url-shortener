package me.dionclei.url_shortener.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.url_shortener.entities.Url;
import me.dionclei.url_shortener.repositories.UrlRepository;
import me.dionclei.url_shortener.services.interfaces.UrlService;

/**
 * This class is the main implementation of UrlService, it
 * generates a shortened url using Base64 and SHA-256 hash
 */

@Service
public class UrlServiceImpl implements UrlService {
	
	@Autowired
	private UrlRepository repository;

	public Url findByShortenedUrl(String url) {
		return repository.findByShortenedUrl(url);
	}

	public Url generateUrl(String originalUrl) {
		Url url = new Url();
		
		url.setCreation(Instant.now());
		url.setExpiresAt(generateExpiresAt());
		url.setOriginalUrl(originalUrl);
		Url savedUrl = repository.save(url);

		// Generate and set the shortened version of the URL
		savedUrl.setShortenedUrl(generateShortenedUrl(savedUrl));

		// Save the updated entity with shortened URL
		return repository.save(savedUrl);
	}

	private String generateShortenedUrl(Url url) {
	    try {
	        String key = url.getId().toString() + "-" + url.getOriginalUrl();

	        // Generate SHA-256 hash of the key
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(key.getBytes(StandardCharsets.UTF_8));

	        // Encode using Base64 URL-safe format without padding
	        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);

	        // Return the first characters as the shortened URL
	        return encoded.substring(0, 7);
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e.getMessage());
	    }
	}

	private Instant generateExpiresAt() {
		return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);
	}
}