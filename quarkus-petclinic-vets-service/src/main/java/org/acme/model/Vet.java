package org.acme.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name="vets")
@Cacheable
public class Vet extends PanacheEntity {

	@Column(name = "first_name")
	public String firstName;

	@Column(name = "last_name")
	public String lastName;

	@ManyToMany
	@JoinTable(
		name = "vet_Specialties",
		joinColumns = @JoinColumn(name = "vet_id"), 
  		inverseJoinColumns = @JoinColumn(name = "specialty_id"))
    public List<Specialty> specialties;
	
}
