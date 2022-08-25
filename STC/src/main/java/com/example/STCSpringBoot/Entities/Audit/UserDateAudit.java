package com.example.STCSpringBoot.Entities.Audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
@JsonIgnoreProperties(
		value = { "createdBY", "updatedBy" },
		allowGetters = true
)
public class UserDateAudit  extends DateAudit{
	private static final long serialVersionUID = 1L;

	@CreatedBy
	@Column(updatable = false)
	private Long createdBy;

	@LastModifiedBy
	private Long updatedBy;
}
