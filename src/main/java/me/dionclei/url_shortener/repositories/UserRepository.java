package me.dionclei.url_shortener.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import me.dionclei.url_shortener.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByEmail(String email);
}
