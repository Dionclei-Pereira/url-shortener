package me.dionclei.url_shortener.config;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.dionclei.url_shortener.exceptions.StandardError;
import me.dionclei.url_shortener.exceptions.TokenException;
import me.dionclei.url_shortener.repositories.UserRepository;
import me.dionclei.url_shortener.services.interfaces.TokenService;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	private TokenService service;
	private UserRepository repository;
	private ObjectMapper mapper;
	
	public SecurityFilter(TokenService service, UserRepository repository, ObjectMapper mapper) {
		this.service = service;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        var token = request.getHeader("Authorization");

        if (token != null) {
            try {
                var subject = service.validateToken(token);
                if (subject != null) {
                    var user = repository.findByEmail(subject);
                    var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (TokenException e) {
            	
                // handling the exception manually because it is inside a filter
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");

                var writer = response.getWriter();
                var error = new StandardError(
                        Instant.now(),
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid token",
                        "Your token is not valid",
                        request.getRequestURI()
                );

                // using jackson to convert to JSON
                writer.write(mapper.writeValueAsString(error));
                writer.flush();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
