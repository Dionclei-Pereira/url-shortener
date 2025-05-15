package me.dionclei.url_shortener.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.url_shortener.entities.Url;

public interface UrlRepository extends JpaRepository<Url, Long>{
	
	Optional<Url> findByShortenedUrl(String url);
}
