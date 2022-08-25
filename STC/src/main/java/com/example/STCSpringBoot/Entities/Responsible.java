package com.example.STCSpringBoot.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Responsible")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Responsible extends User {
	private static final long serialVersionUID = 1L;

	@Column(name = "Domaine")
	private String Domaine;

	@Column(name = "Type")
	private String Type;

	@Column(name = "Etat")
	private String Etat;
	
	@OneToMany(mappedBy = "responsible")
	private List<Activity> activity;
}
