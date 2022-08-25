package com.example.STCSpringBoot.Entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	@Column(name = "Titre")
	private String Titre;
	
	@Column(name = "Descriptif")
	private String Descriptif;

	@Column(name = "type")
	private String type;
	
	//@Temporal(TemporalType.DATE)
	@Column(name = "DateDebut")
	private Date DateDebut;

	@Column(name = "DateFin")
	private Date DateFin;

	@Column(name = "Etat")
	private String Etat;
	
	
	@ManyToOne
	@JoinColumn(name = "ID_Exer")
	private Exercise exercise;

	@ManyToOne
	@JoinColumn(name = "ID_User_res")
	private Responsible responsible;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
			  name = "participant_activite", 
			  joinColumns = @JoinColumn(name = "ID_Participant"), 
			  inverseJoinColumns = @JoinColumn(name = "ID_Activite"))
	private List<Participant> participant;
}
