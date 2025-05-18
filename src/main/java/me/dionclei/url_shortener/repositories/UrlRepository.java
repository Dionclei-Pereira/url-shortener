package me.dionclei.url_shortener.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import me.dionclei.url_shortener.entities.Url;

public interface UrlRepository extends JpaRepository<Url, Long>{
	
	Optional<Url> findByShortenedUrl(String url);
	
	Page<Url> findByUserId(Long userId, Pageable pageable);
	
	@Modifying
	@Transactional()
	@Query(nativeQuery = true, value = """
			DELETE FROM url WHERE expires_at < NOW()
			""")
	void deleteExpired();
}
