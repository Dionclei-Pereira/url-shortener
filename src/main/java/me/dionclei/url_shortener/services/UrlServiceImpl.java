package me.dionclei.url_shortener.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import me.dionclei.url_shortener.dto.GenericPage;
import me.dionclei.url_shortener.entities.Url;
import me.dionclei.url_shortener.exceptions.ResourceNotFoundException;
import me.dionclei.url_shortener.exceptions.UrlException;
import me.dionclei.url_shortener.repositories.UrlRepository;
import me.dionclei.url_shortener.services.interfaces.UrlService;
import me.dionclei.url_shortener.services.interfaces.UserService;

/**
 * This class is the main implementation of UrlService, it
 * generates a shortened url using Base64 and SHA-256 hash
 */

@Service
public class UrlServiceImpl implements UrlService {
	
	private UrlRepository repository;
	private UserService userService;
	
	public UrlServiceImpl(UrlRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}
	
	@Cacheable(value = "urlByShortened", key = "#url")
	public Url findByShortenedUrl(String url) {
		return repository.findByShortenedUrl(url).orElseThrow(() -> new ResourceNotFoundException("Url not found"));
	}
	
	@Cacheable(value = "allUrls", key = "#userEmail + '_' + #page")
	public GenericPage<Url> getAllUrls(String userEmail, Integer page) {
		Pageable pageable = PageRequest.of(page, 10);
		var user = userService.findByEmail(userEmail);
		var p = repository.findByUserId(user.get().getId(), pageable);
		return new GenericPage<Url>(p);
	}
	
	@CacheEvict(value = "allUrls", allEntries = true)
	public Url generateUrl(String originalUrl, String userEmail) {
		Url url = new Url();
		var user = userService.findByEmail(userEmail);
		url.setUser(user.get());
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
	        throw new UrlException(e.getMessage());
	    }
	}

	private Instant generateExpiresAt() {
		return LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.UTC);
	}
}