package me.dionclei.url_shortener.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import me.dionclei.url_shortener.entities.Url;
import me.dionclei.url_shortener.entities.User;
import me.dionclei.url_shortener.enums.UserRole;
import me.dionclei.url_shortener.repositories.UrlRepository;
import me.dionclei.url_shortener.repositories.UserRepository;

@Configuration
public class ConfigRunner implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UrlRepository urlRepository;
	
	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByEmail("dionclei2@gmail.com") == null) {
			
			User user1 = new User(null,"Dionclei",  "12345678", "dionclei2@gmail.com", null, UserRole.ADMIN);
			userRepository.save(user1);
		
			Url url = new Url(null, user1,
				"https://www.linkedin.com/in/dionclei-de-souza-pereira-07287726b/", "link",
				Instant.now(), Instant.now());
			urlRepository.save(url);
		
			user1.setUrls(Arrays.asList(url));
			userRepository.save(user1);
		}
	}
}
