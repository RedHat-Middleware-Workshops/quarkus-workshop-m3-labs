package org.acme.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "vet_specialties")
@Cacheable
public class VetSpecialty extends PanacheEntity {

    @Column(name = "vet_id")
    public Long vetId;

    @Column(name = "specialty_id")
	public Long specialtyId;
    
}
