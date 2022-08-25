package com.example.STCSpringBoot.Repository;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.STCSpringBoot.Entities.User;
import com.example.STCSpringBoot.Exception.ResourceNotFoundException;
import com.example.STCSpringBoot.Security.UserPrincipal;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(@NotBlank String username);

	Optional<User> findByEmail(@NotBlank String email);

	Boolean existsByUsername(@NotBlank String username);

	Boolean existsByEmail(@NotBlank String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	default User getUser(UserPrincipal currentUser) {
		return getUserByName(currentUser.getUsername());
	}

	default User getUserByName(String username) {
		return findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}
}
