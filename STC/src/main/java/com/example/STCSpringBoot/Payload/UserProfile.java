package com.example.STCSpringBoot.Payload;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
	private Long id;
	private String username;
	private String prenom;
	private String nom;
	private Instant joinedAt;
	private String email;
	private String telephone;
	
}
