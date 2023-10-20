package org.acme.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.acme.dto.PetDetails;
import org.acme.dto.PetRequest;
import org.acme.model.Pet;
import org.acme.model.PetType;
import org.acme.model.Visit;
import org.acme.rest.client.OwnersRestClient;
import org.acme.rest.client.VisitsRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class PetsService {
   
    static Logger LOG = Logger.getLogger(PetsService.class);

    @Inject
    @RestClient
    OwnersRestClient ownersRestClient;

    @Inject
    @RestClient
    VisitsRestClient visitsRestClient;

    public Pet findById(Long id) {

        PetDetails petDetails = ownersRestClient.findPetById(id.longValue());

        Pet pet = new Pet(petDetails);
        assignPetVisits(pet);

        return pet; 
    }

	public PetType findByPetTypeName(String type) {
		return ownersRestClient.findPetTypeByName(type);
	}

	public void update(Pet existingPet, Long ownerId) {

        PetRequest petRequest = new PetRequest(existingPet);
        ownersRestClient.updatePet(ownerId, existingPet.getId().longValue(), petRequest);
	}

	public void save(Pet newPet, Long ownerId) {

        PetRequest petRequest = new PetRequest(newPet);
        ownersRestClient.addPetToOwner(ownerId.longValue(), petRequest);
	}

    private void assignPetVisits(Pet thePet) {
        LOG.debug("assigning pets visits for single pet");

        List<Visit> visitsCollection = visitsRestClient.visits(thePet.getId().longValue());

        visitsCollection.forEach(tempVisit -> {

            if (thePet.visits == null) {
                thePet.visits = new ArrayList<>();
            }

            thePet.visits.add(tempVisit);
        });
    }

}
