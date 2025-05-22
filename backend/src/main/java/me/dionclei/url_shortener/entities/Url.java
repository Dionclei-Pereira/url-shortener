package me.dionclei.url_shortener.entities;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Url {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String originalUrl;
	private String shortenedUrl;
	private Instant creation;
	private Instant expiresAt;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	public Url() {}

	public Url(Long id, User user,  String originalUrl, String shortenedUrl, Instant creation, Instant expiresAt) {
		super();
		this.id = id;
		this.user = user;
		this.originalUrl = originalUrl;
		this.shortenedUrl = shortenedUrl;
		this.creation = creation;
		this.expiresAt = expiresAt;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
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
		return shortenedUrl;
	}

	public void setShortenedUrl(String shortenedUrl) {
		this.shortenedUrl = shortenedUrl;
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
