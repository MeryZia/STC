package com.example.STCSpringBoot.Security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.STCSpringBoot.Entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrincipal  implements UserDetails{
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String prenom;

	private String nom;

	private String username;
	
	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;
	
	
	private Collection<? extends GrantedAuthority> authorities;


	public UserPrincipal(Long id, String prenom, String nom, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.username = username;
		this.email = email;
		this.password = password;

		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
	}
	
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrincipal(user.getID(), user.getPrenom(), user.getNom(), user.getUsername(),
				user.getEmail(), user.getPassword(), authorities);
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities == null ? null : new ArrayList<>(authorities);
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) object;
		return Objects.equals(id, that.id);
	}

	public int hashCode() {
		return Objects.hash(id);
	}

	

	public String getPrenom() {
		return prenom;
	}

	public String getNom() {
		return nom;
	}

}
