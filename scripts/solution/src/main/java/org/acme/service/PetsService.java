package org.acme.service;

import javax.enterprise.context.ApplicationScoped;
import org.acme.model.Pet;

@ApplicationScoped
public class PetsService {

    public Pet findById(Long id) {
        return Pet.findById(id.longValue());
    }

}