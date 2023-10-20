package org.acme.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.acme.dto.PetRequest;
import org.acme.model.Pet;
import org.acme.model.PetType;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class PetsService {
   
    public List<PetType> findPetTypes() {
        return PetType.listAll(Sort.by("name"));
    }

    public PetType findByPetTypeId(long typeId) {
        return PetType.findById(typeId);
    }

    public PetType findByPetTypeName(String name) {
        return PetType.findByName(name);
    }

    public Pet findById(long id) {
        return Pet.findById(id);
    }

    @Transactional
    public void save(Pet pet) {
        pet.persist();
    }

    @Transactional
	public Pet update(long petId, PetRequest petRequest) {
        Pet pet = findById(petId);

        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

        PetType petType = findByPetTypeId(petRequest.getTypeId());
        pet.setType(petType);

        pet.persist();

        return pet;
	}

}
