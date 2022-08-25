package com.example.STCSpringBoot.Service;

import com.example.STCSpringBoot.Entities.User;
import com.example.STCSpringBoot.Payload.ApiResponse;
import com.example.STCSpringBoot.Payload.UserIdentityAvailability;
import com.example.STCSpringBoot.Payload.UserProfile;
import com.example.STCSpringBoot.Payload.UserSummary;
import com.example.STCSpringBoot.Security.UserPrincipal;

public interface UserService {
	UserSummary getCurrentUser(UserPrincipal currentUser);

	UserIdentityAvailability checkUsernameAvailability(String username);

	UserIdentityAvailability checkEmailAvailability(String email);

	UserProfile getUserProfile(String username);

	User addUser(User user);

	User updateUser(User newUser, String username, UserPrincipal currentUser);

	ApiResponse deleteUser(String username, UserPrincipal currentUser);

	ApiResponse giveAdmin(String username);

	ApiResponse removeAdmin(String username);

	UserProfile setOrUpdateInfo(UserPrincipal currentUser);

}
