package me.dionclei.url_shortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dionclei.url_shortener.entities.Url;
import me.dionclei.url_shortener.repositories.UrlRepository;
import me.dionclei.url_shortener.services.interfaces.UrlService;

@Service
public class UrlServiceImpl implements UrlService {
	
	@Autowired
	private UrlRepository repository;
	
	public Url findByShortenedUrl(String url) {
		return repository.findByShortenedUrl(url);
	}
}
