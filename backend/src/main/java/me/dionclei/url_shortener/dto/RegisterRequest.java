package me.dionclei.url_shortener.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
		
		@NotBlank(message = "Name is required")
		@Size(min = 3, max = 16, message = "Name must be between 3 and 16")
		String name,
		
		@NotBlank(message = "Email is required")
		@Size(min = 3, max = 30, message = "Email must be between 3 and 30")
		@Email(message = "Email must be valid")
		String email,
		
		@NotBlank(message = "Password is required")
		@Size(min = 3, max = 15, message = "Password must be between 3 and 15")
		String password) {

}
