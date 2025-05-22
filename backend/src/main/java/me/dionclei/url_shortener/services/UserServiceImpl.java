package me.dionclei.url_shortener.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import me.dionclei.url_shortener.entities.User;
import me.dionclei.url_shortener.repositories.UserRepository;
import me.dionclei.url_shortener.services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Cacheable(value = "userByEmail", key = "#email")
	public Optional<User> findByEmail(String email) {
		return repository.findUserByEmail(email);
	}

}
