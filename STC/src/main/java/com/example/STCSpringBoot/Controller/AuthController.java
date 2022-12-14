package com.example.STCSpringBoot.Controller;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.STCSpringBoot.Entities.User;
import com.example.STCSpringBoot.Entities.Role.Role;
import com.example.STCSpringBoot.Entities.Role.RoleName;
import com.example.STCSpringBoot.Exception.AppException;
import com.example.STCSpringBoot.Exception.BlogapiException;
import com.example.STCSpringBoot.Payload.ApiResponse;
import com.example.STCSpringBoot.Payload.JwtAuthenticationResponse;
import com.example.STCSpringBoot.Payload.LoginRequest;
import com.example.STCSpringBoot.Payload.SignUpRequest;
import com.example.STCSpringBoot.Repository.RoleRepository;
import com.example.STCSpringBoot.Repository.UserRepository;
import com.example.STCSpringBoot.Security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final String USER_ROLE_NOT_SET = "User role not set";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Username is already taken");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new BlogapiException(HttpStatus.BAD_REQUEST, "Email is already taken");
		}

		String firstName = signUpRequest.getPrenom().toLowerCase();

		String lastName = signUpRequest.getNom().toLowerCase();

		String username = signUpRequest.getUsername().toLowerCase();

		String email = signUpRequest.getEmail().toLowerCase();

		String password = passwordEncoder.encode(signUpRequest.getPassword());
		
		String tele = signUpRequest.getTelephone();

		User user = new User(firstName, lastName, username, email,tele, password);

		List<Role> roles = new ArrayList<>();

		if (userRepository.count() == 0) {
			roles.add(roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
			roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
		} else {
			roles.add(roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));
		}

		user.setRoles(roles);

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
				.buildAndExpand(result.getID()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
	}
}
