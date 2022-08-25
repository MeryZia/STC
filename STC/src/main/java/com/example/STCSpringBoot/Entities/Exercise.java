package com.example.STCSpringBoot.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Exercise")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;

	@Column(name = "Annee")
	private int Annee;

	@Column(name = "DateDebut")
	private Date DateDebut;

	@Column(name = "DateFin")
	private Date DateFin;

	@Column(name = "Statut")
	private String Statut;
	
	@OneToMany(mappedBy = "exercise")
	private List<Activity> activity;
}
