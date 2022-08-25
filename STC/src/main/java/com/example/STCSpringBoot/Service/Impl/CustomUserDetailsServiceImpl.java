package com.example.STCSpringBoot.Service.Impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.STCSpringBoot.Entities.User;
import com.example.STCSpringBoot.Repository.UserRepository;
import com.example.STCSpringBoot.Security.UserPrincipal;
import com.example.STCSpringBoot.Service.CustomUserDetailsService;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

		return UserPrincipal.create(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with this username or email: %s", usernameOrEmail)));
		return UserPrincipal.create(user);
	}

}
