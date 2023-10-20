package org.acme.model;

import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name="specialties")
@Cacheable
public class Specialty extends PanacheEntity {
  
    public String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "specialties")
    public List<Vet> vets;

}