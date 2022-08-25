package com.example.STCSpringBoot.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {
	private Long id;
	private String username;
	private String prenom;
	private String nom;
}
