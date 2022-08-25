package com.example.STCSpringBoot.Entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Participant")
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant  extends User{
	private static final long serialVersionUID = 1L;

	@Column(name = "Domaine")
	private String Domaine;

	@Column(name = "Structure")
	private String Structure;
	
	@ManyToMany(mappedBy = "participant", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Activity> activite;
}
