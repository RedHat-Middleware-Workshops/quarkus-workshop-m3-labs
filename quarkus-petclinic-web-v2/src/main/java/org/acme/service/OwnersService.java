package org.acme.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import org.acme.model.Owner;
import org.acme.model.Pet;
import org.acme.model.Visit;
import org.acme.rest.client.OwnersRestClient;
import org.acme.rest.client.VisitsRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@ApplicationScoped
public class OwnersService {

    static Logger LOG = Logger.getLogger(OwnersService.class);
    
    @Inject
    @RestClient
    VisitsRestClient visitsRestClient;

    @Inject
    @RestClient
    OwnersRestClient ownersRestClient;

    public List<Owner> findByLastName(String lastName) {

        return ownersRestClient.findOwnerByLastName(lastName);
    }

    public Owner findById(Long id) {
        Owner theOwner = ownersRestClient.findOwnerById(id.longValue());

        assignPetVisitsMulti(theOwner);

        return theOwner;
    }

    public void updateOwner(long ownerId, Owner ownerRequest) {
        ownersRestClient.updateOwner(ownerId, ownerRequest);
    }

	public long createOwner(Owner newOwner) {
        Response response = ownersRestClient.createOwner(newOwner);

        Owner responseOwner = response.readEntity(Owner.class);

		return responseOwner.getId();
	}

    private void assignPetVisitsMulti(Owner theOwner) {
        List<Pet> thePets = theOwner.pets;

        LOG.debug("assigning pets visits");

        Map<Long, Pet> thePetsMap = thePets.stream()
                .collect(Collectors.toMap(Pet::getId, pets -> pets));

        List<Long> petIds = new ArrayList<>(thePetsMap.keySet());
        List<Visit> visitsCollection = visitsRestClient.visitsMultiGet(petIds);

        visitsCollection.forEach(tempVisit -> {
            long petId = tempVisit.petId;

            Pet thePet = thePetsMap.get(petId);

            if (thePet.visits == null) {
                thePet.visits = new ArrayList<>();
            }

            thePet.visits.add(tempVisit);
        });
    }

}