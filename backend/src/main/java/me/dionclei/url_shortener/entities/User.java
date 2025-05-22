package me.dionclei.url_shortener.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import me.dionclei.url_shortener.enums.UserRole;

@Entity
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String password;
	private String email;
	private UserRole role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Url> urls;

	public User() {}
	
	public User(Long id,String name, String password, String email, List<Url> urls, UserRole role) {
		super();
		this.id = id;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.email = email;
		this.urls = urls;
		this.name = name;
		this.role = role;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Url> getUrls() {
		return urls;
	}

	public void setUrls(List<Url> urls) {
		this.urls = urls;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		// Check if the user's role is ADMIN
		// If so, grant both ADMIN and USER authorities
	    // This is common because an admin typically has all regular user privileges as well
		if (role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		
		// Otherwise, grant only the standard USER authority
		else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getUsername() {
		// The login method uses the email for authentication
		return email;
	}
}
