package org.acme.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Visit {
  
	public Long id;

	public Long petId;

	@JsonFormat(pattern = "yyyy-MM-dd")
	public LocalDate date;

	@NotEmpty
	public String description;

	public Long getPetId() {
		return petId;
	}

	public void setPetId(Long petId) {
		this.petId = petId;
	}
}