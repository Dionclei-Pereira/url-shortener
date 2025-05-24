package me.dionclei.url_shortener.controllers;

import java.util.ArrayList;

import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
			return ResponseEntity.badRequest().build();
		}

	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest request) throws MethodArgumentNotValidException {
		var userDetails = userRepository.findByEmail(request.email());
		if (userDetails == null) {
			var user = new User(null, request.name(), request.password(),
					request.email(), new ArrayList<>(), UserRole.USER);
			
			var savedUser = userRepository.save(user);
			return ResponseEntity.ok().body(savedUser);
		}
		
        BindingResult bindingResult = new BeanPropertyBindingResult(request.email(), "email");
        bindingResult.addError(new FieldError("email", "email", request.email(), false, null, null, "email must be unique"));

        MethodParameter methodParameter = new MethodParameter(this.getClass().getDeclaredMethods()[0], -1);

        throw new MethodArgumentNotValidException(methodParameter, bindingResult);
   
	}
	
	@GetMapping("isvalid")
	public ResponseEntity<Boolean> isValid(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		return ResponseEntity.ok().body(tokenService.isValid(token));
	}
}
