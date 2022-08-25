package com.example.STCSpringBoot.Entities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.example.STCSpringBoot.Entities.Audit.DateAudit;
import com.example.STCSpringBoot.Entities.Role.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;

import javax.validation.constraints.Email;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User extends DateAudit {
	
	private static final long serialVersionUID = 1L;

	@Id//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@NotBlank
	@Column(name = "Nom")
	private String Nom;

	@NotBlank
	@Column(name = "Prenom")
	private String Prenom;

	@NotBlank
	@Column(name = "Login")
	private String Username;

	@NotBlank
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "Password")
	private String Password;
	
	@NotBlank
	@NaturalId
	@Email
	@Column(name = "Email")
	private String Email;

	@Column(name = "Telephone")
	private String Telephone;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;
	

	public User(String nom,String prenom, String username,String password,String email, String telephone) {
		super();
		Nom = nom;
		Prenom = prenom;
		Username = username;
		Password = password;
		Email = email;
		Telephone = telephone;
	}
	
	
	public List<Role> getRoles() {

		return roles == null ? null : new ArrayList<>(roles);
	}

	public void setRoles(List<Role> roles) {

		if (roles == null) {
			this.roles = null;
		} else {
			this.roles = Collections.unmodifiableList(roles);
		}
	}
}
