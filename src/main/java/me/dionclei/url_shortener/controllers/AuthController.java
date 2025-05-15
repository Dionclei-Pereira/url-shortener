package me.dionclei.url_shortener.controllers;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dionclei.url_shortener.dto.LoginRequest;
import me.dionclei.url_shortener.dto.LoginResponse;
import me.dionclei.url_shortener.dto.RegisterRequest;
import me.dionclei.url_shortener.entities.User;
import me.dionclei.url_shortener.enums.UserRole;
import me.dionclei.url_shortener.repositories.UserRepository;
import me.dionclei.url_shortener.services.interfaces.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserRepository userRepository;
	private TokenService tokenService;
	private AuthenticationManager manager;
	
	public AuthController(UserRepository userRepository, TokenService tokenService, AuthenticationManager manager) {
		this.userRepository = userRepository;
		this.tokenService = tokenService;
		this.manager = manager;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		try {
			var username = new UsernamePasswordAuthenticationToken(request.email(), request.password());
			var auth = manager.authenticate(username);
			var token = tokenService.generateToken((User) auth.getPrincipal());
			
			return ResponseEntity.ok().body(new LoginResponse(token));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new LoginResponse(e.getMessage()));
		}

	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
		var userDetails = userRepository.findByEmail(request.email());
		if (userDetails == null) {
			var user = new User(null, request.name(), request.password(),
					request.email(), new ArrayList<>(), UserRole.USER);
			
			var savedUser = userRepository.save(user);
			return ResponseEntity.ok().body(savedUser);
		}
		
		return ResponseEntity.badRequest().build();
	}
}
