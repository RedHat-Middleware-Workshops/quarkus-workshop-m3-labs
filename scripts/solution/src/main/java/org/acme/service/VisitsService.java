package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.model.Pet;

@ApplicationScoped
public class VisitsService {

    public Pet findById(Long id) {
        return Pet.findById(id.longValue());
    }

}