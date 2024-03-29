package org.acme.model;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "types")
@Cacheable
public class PetType extends PanacheEntity {
  
    public String name;

    public static PetType findByName(String name) {

        return find("name", name).firstResult();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}