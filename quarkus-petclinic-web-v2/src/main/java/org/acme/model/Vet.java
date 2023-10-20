package org.acme.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class Vet {

	public long id;
	
	@NotEmpty
	public String firstName;

	@NotEmpty
	public String lastName;

    public List<Specialty> specialties;
	
}
