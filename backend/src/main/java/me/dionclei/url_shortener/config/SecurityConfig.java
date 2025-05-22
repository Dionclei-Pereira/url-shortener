package me.dionclei.url_shortener.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, SecurityFilter filter) throws Exception {
		return http.csrf(c -> c.disable())
				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> 
						auth.requestMatchers("/auth/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/url/{url}").permitAll()
						.anyRequest().authenticated())
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}
