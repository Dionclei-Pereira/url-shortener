package me.dionclei.url_shortener.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.url_shortener.dto.UrlRequest;
import me.dionclei.url_shortener.dto.UrlResponse;
import me.dionclei.url_shortener.services.interfaces.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController {
	
	private UrlService urlService;
	
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@GetMapping("/{url}")
	public ResponseEntity<Void> getUrl(@PathVariable String url) {
		var urlObj = urlService.findByShortenedUrl(url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", urlObj.getOriginalUrl());
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<UrlResponse> generateUrl(@RequestBody UrlRequest request) {
		var url = urlService.generateUrl(request.originalUrl());
		
		var response = new UrlResponse(url.getShortenedUrl());
		
		return ResponseEntity.ok().body(response);
	}
}
