package me.dionclei.url_shortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import me.dionclei.url_shortener.entities.Url;

public interface UrlRepository extends JpaRepository<Url, Long>{
	
	Url findByShortenedUrl(String url);
}
