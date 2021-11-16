package org.acme.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class PetRequest {

    public long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate birthDate;

    public String name;

    public int typeId;

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