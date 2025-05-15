package me.dionclei.url_shortener.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.dionclei.url_shortener.repositories.UserRepository;
import me.dionclei.url_shortener.services.interfaces.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	private TokenService service;
	private UserRepository repository;
	
	public SecurityFilter(TokenService service, UserRepository repository) {
		this.service = service;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = request.getHeader("Authorization");
		if (token != null) {
			String subject = service.validateToken(token);
			var userDetails = repository.findByEmail(subject);
			var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}

}
