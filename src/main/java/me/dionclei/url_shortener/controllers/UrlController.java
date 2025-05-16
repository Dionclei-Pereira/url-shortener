package me.dionclei.url_shortener.controllers;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.url_shortener.dto.GenericPage;
import me.dionclei.url_shortener.dto.UrlRequest;
import me.dionclei.url_shortener.dto.UrlResponse;
import me.dionclei.url_shortener.entities.Url;
import me.dionclei.url_shortener.services.interfaces.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController {
	
	private UrlService urlService;
	
	public UrlController(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@GetMapping
	public ResponseEntity<GenericPage<Url>> getUrls(@RequestParam(defaultValue = "0") Integer page, Principal principal) {
		var urls = urlService.getAllUrls(principal.getName(), page);
		return ResponseEntity.ok().body(urls);
	}
	
	@GetMapping("/{url}")
	public ResponseEntity<Void> getUrl(@PathVariable String url) {
		var urlObj = urlService.findByShortenedUrl(url);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", urlObj.getOriginalUrl());
		return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
	
	@PostMapping
	public ResponseEntity<UrlResponse> generateUrl(@RequestBody UrlRequest request, Principal principal) {
		var url = urlService.generateUrl(request.originalUrl(), principal.getName());
		
		var response = new UrlResponse(url.getShortenedUrl());
		
		return ResponseEntity.ok().body(response);
	}
}
