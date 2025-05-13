package me.dionclei.url_shortener.services.interfaces;

import me.dionclei.url_shortener.entities.Url;

public interface UrlService {
	
	Url findByShortenedUrl(String url);
	
}
