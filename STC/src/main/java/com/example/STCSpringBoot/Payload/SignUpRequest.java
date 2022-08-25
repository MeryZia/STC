package com.example.STCSpringBoot.Payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest {
	@NotBlank
	@Size(min = 4, max = 40)
	private String prenom;

	@NotBlank
	@Size(min = 4, max = 40)
	private String nom;

	@NotBlank
	@Size(min = 3, max = 15)
	private String username;

	@NotBlank
	@Size(max = 40)
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 4, max = 40)
	private String telephone;

	@NotBlank
	@Size(min = 6, max = 20)
	private String password;
}
