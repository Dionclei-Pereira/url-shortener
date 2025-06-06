package me.dionclei.url_shortener.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import me.dionclei.url_shortener.services.interfaces.UrlService;

@Component
public class UrlCleanService {
	
	private UrlService urlService;
	
	public UrlCleanService(UrlService urlService) {
		this.urlService = urlService;
	}
	
	@Scheduled(cron = "0 0,30 * * * *")
	public void clean() {
		System.out.println("Cleaning URLs");
		urlService.clean();
	}
}
