package me.dionclei.url_shortener.entities;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String originalUrl;
	private String ShortenedUrl;
	private Instant creation;
	private Instant expiresAt;
	
	public Url() {}

	public Url(Long id, String originalUrl, String shortenedUrl, Instant creation, Instant expiresAt) {
		super();
		this.id = id;
		this.originalUrl = originalUrl;
		ShortenedUrl = shortenedUrl;
		this.creation = creation;
		this.expiresAt = expiresAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getShortenedUrl() {
		return ShortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		ShortenedUrl = shortenedUrl;
	}

	public Instant getCreation() {
		return creation;
	}

	public void setCreation(Instant creation) {
		this.creation = creation;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
	
}
