package me.dionclei.url_shortener.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import me.dionclei.url_shortener.entities.User;
import me.dionclei.url_shortener.services.interfaces.TokenService;

/**
 * This class implements TokenService
 * 
 * this service is the main implementation of the interface
 * and generates a token with the user email and JWT
 */
@Service
public class TokenServiceHMAC implements TokenService {
	
	private static String key = "c7c4cbc3cc4b7a54b51f8ad95dc24d000ae025686feeca549f3913f561faf1c9";
	
	@Override
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.require(algorithm)
					.withIssuer("URL-BACKEND")
					.build()
					.verify(token)
					.getSubject();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(key);
			return JWT.create().withIssuer("URL-BACKEND")
					.withExpiresAt(generateExpiresAt())
					.withSubject(user.getEmail())
					.sign(algorithm);
		} catch (Exception e) {
			return null;
		}
	}
	
	private Instant generateExpiresAt() {
		return LocalDateTime.now().plusDays(3).toInstant(ZoneOffset.UTC);
	}

}
