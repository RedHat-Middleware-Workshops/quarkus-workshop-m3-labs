package org.acme.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.acme.model.Pet;

public class PetRequest {

    public long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate birthDate;

    @Size(min = 1)
    public String name;

    public int typeId;

    public PetRequest() {

    }

    public PetRequest(Pet thePet) {

        if (thePet.getId() != null) {
            id = thePet.getId();
        }

        birthDate = thePet.getBirthDate();
        name = thePet.getName();
        typeId = thePet.getType().getId().intValue();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    
}
