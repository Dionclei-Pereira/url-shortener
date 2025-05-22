package me.dionclei.url_shortener.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import me.dionclei.url_shortener.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	UserDetails findByEmail(String email);
	
	@Query(nativeQuery = true, value = """
			SELECT * FROM user WHERE email = :email
			""")
	Optional<User> findUserByEmail(String email);
}
