package com.example.STCSpringBoot.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.STCSpringBoot.Entities.User;
import com.example.STCSpringBoot.Entities.Role.Role;
import com.example.STCSpringBoot.Entities.Role.RoleName;
import com.example.STCSpringBoot.Exception.AccessDeniedException;
import com.example.STCSpringBoot.Exception.AppException;
import com.example.STCSpringBoot.Exception.BadRequestException;
import com.example.STCSpringBoot.Exception.ResourceNotFoundException;
import com.example.STCSpringBoot.Exception.UnauthorizedException;
import com.example.STCSpringBoot.Payload.ApiResponse;
import com.example.STCSpringBoot.Payload.UserIdentityAvailability;
import com.example.STCSpringBoot.Payload.UserProfile;
import com.example.STCSpringBoot.Payload.UserSummary;
import com.example.STCSpringBoot.Repository.RoleRepository;
import com.example.STCSpringBoot.Repository.UserRepository;
import com.example.STCSpringBoot.Security.UserPrincipal;
import com.example.STCSpringBoot.Service.UserService;

@Service
public class UserServiceImpl  implements UserService{

	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Override
	public UserSummary getCurrentUser(UserPrincipal currentUser) {
		return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getPrenom(),
				currentUser.getNom());
	}

	@Override
	public UserIdentityAvailability checkUsernameAvailability(String username) {
		Boolean isAvailable = !userRepository.existsByUsername(username);
		return new UserIdentityAvailability(isAvailable);
	}

	@Override
	public UserIdentityAvailability checkEmailAvailability(String email) {
		Boolean isAvailable = !userRepository.existsByEmail(email);
		return new UserIdentityAvailability(isAvailable);
	}

	@Override
	public UserProfile getUserProfile(String username) {
		User user = userRepository.getUserByName(username);

		return new UserProfile(user.getID(), user.getUsername(), user.getPrenom(), user.getNom(),
				user.getCreatedAt(), user.getEmail(), user.getTelephone());
	}

	@Override
	public User addUser(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Username is already taken");
			throw new BadRequestException(apiResponse);
		}

		if (userRepository.existsByEmail(user.getEmail())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Email is already taken");
			throw new BadRequestException(apiResponse);
		}

		List<Role> roles = new ArrayList<>();
		roles.add(
				roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException("User role not set")));
		user.setRoles(roles);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User newUser, String username, UserPrincipal currentUser) {
		User user = userRepository.getUserByName(username);
		if (user.getID().equals(currentUser.getId())
				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			user.setPrenom(newUser.getPrenom());
			user.setNom(newUser.getNom());
			user.setPassword(passwordEncoder.encode(newUser.getPassword()));
			user.setTelephone(newUser.getTelephone());	

			return userRepository.save(user);
		}

		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update profile of: " + username);
		throw new UnauthorizedException(apiResponse);
	}

	@Override
	public ApiResponse deleteUser(String username, UserPrincipal currentUser) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", username));
		if (!user.getID().equals(currentUser.getId()) || !currentUser.getAuthorities()
				.contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + username);
			throw new AccessDeniedException(apiResponse);
		}

		userRepository.deleteById(user.getID());

		return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
	}

	@Override
	public ApiResponse giveAdmin(String username) {
		User user = userRepository.getUserByName(username);
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException("User role not set")));
		roles.add(roleRepository.findByName(RoleName.ROLE_RESPONSIBLE).orElseThrow(() -> new AppException("User role not set")));
		roles.add(roleRepository.findByName(RoleName.ROLE_PARTICIPANT).orElseThrow(() -> new AppException("User role not set")));
		user.setRoles(roles);
		userRepository.save(user);
		return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + username);
	}

	@Override
	public ApiResponse removeAdmin(String username) {
		User user = userRepository.getUserByName(username);
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findByName(RoleName.ROLE_PARTICIPANT).orElseThrow(() -> new AppException("User role not set")));
		roles.add(roleRepository.findByName(RoleName.ROLE_RESPONSIBLE).orElseThrow(() -> new AppException("User role not set")));
		user.setRoles(roles);
		userRepository.save(user);
		return new ApiResponse(Boolean.TRUE, "You took ADMIN role from user: " + username);
	}

	@Override
	public UserProfile setOrUpdateInfo(UserPrincipal currentUser) {
//		User user = userRepository.findByUsername(currentUser.getUsername())
//				.orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUser.getUsername()));
//		Geo geo = new Geo(infoRequest.getLat(), infoRequest.getLng());
//		Address address = new Address(infoRequest.getStreet(), infoRequest.getSuite(), infoRequest.getCity(),
//				infoRequest.getZipcode(), geo);
//		Company company = new Company(infoRequest.getCompanyName(), infoRequest.getCatchPhrase(), infoRequest.getBs());
//		if (user.getId().equals(currentUser.getId())
//				|| currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
//			user.setAddress(address);
//			user.setCompany(company);
//			user.setWebsite(infoRequest.getWebsite());
//			user.setPhone(infoRequest.getPhone());
//			User updatedUser = userRepository.save(user);
//
//			Long postCount = postRepository.countByCreatedBy(updatedUser.getId());
//
//			return new UserProfile(updatedUser.getId(), updatedUser.getUsername(),
//					updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getCreatedAt(),
//					updatedUser.getEmail(), updatedUser.getAddress(), updatedUser.getPhone(), updatedUser.getWebsite(),
//					updatedUser.getCompany(), postCount);
//		}
//
//		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update users profile", HttpStatus.FORBIDDEN);
//		throw new AccessDeniedException(apiResponse);
		return null;
	}

}
