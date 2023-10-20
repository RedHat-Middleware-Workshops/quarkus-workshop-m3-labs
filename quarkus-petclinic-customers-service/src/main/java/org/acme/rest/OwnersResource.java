package org.acme.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

import org.acme.dto.PetDetails;
import org.acme.dto.PetRequest;
import org.acme.model.Owner;
import org.acme.model.Pet;
import org.acme.model.PetType;
import org.acme.service.OwnersService;
import org.acme.service.PetsService;
import org.jboss.logging.Logger;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OwnersResource {

    static Logger LOG = Logger.getLogger(OwnersResource.class);

    @Inject
    OwnersService ownersService;

    @Inject
    PetsService petsService;

    @GET
    @Path("owners")
    public List<Owner> findAll() {
        LOG.debug("findAll");
        return ownersService.findAll();
    }

    @GET
    @Path("owners/{ownerId}")
    public Owner findOwner(@PathParam("ownerId") long ownerId) {
        LOG.debug("findOwner: " + ownerId);
        return ownersService.findOwner(ownerId);
    }

    @GET
    @Path("owners/filter")
    public List<Owner> findOwnerByLastName(@QueryParam("lastName") String lastName) {
        LOG.debug("findOwnerByLastName: " + lastName);
        return ownersService.findByLastName(lastName);
    }

    @POST
    @Path("owners")
    public Response createOwner(Owner theOwner, @Context UriInfo uriInfo) {

        LOG.debug("Creating owner: " + theOwner.lastName);
        ownersService.save(theOwner);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();

        return Response.created(uriBuilder.build()).entity(theOwner).build();
    }

    @PUT 
    @Path("owners/{ownerId}")
    public Response updateOwner(@PathParam("ownerId") long ownerId, Owner ownerRequest) {

        LOG.debug("Updating owner: " + ownerId);
        ownersService.update(ownerId, ownerRequest);

        return Response.noContent().build();
    }


    @GET
    @Path("petTypes")
    public List<PetType> getPetTypes() {
        LOG.debug("getPetTypes");
        return petsService.findPetTypes();
    }

    @GET
    @Path("petTypes/{petTypeId}")
    public PetType findPetTypeById(@PathParam("petTypeId") long petTypeId) {
        LOG.debug("findPetTypeById: " + petTypeId);
        PetType petType = petsService.findByPetTypeId(petTypeId);

        LOG.debug(petType.getName());

        return petType;
    }

    @GET
    @Path("petTypes/filter")
    public PetType findPetTypeByName(@QueryParam("petTypeName") String petTypeName) {
        LOG.debug("findPetTypeByName: " + petTypeName);
        PetType petType = petsService.findByPetTypeName(petTypeName);

        LOG.debug("petType: "  + petType.id + ", " + petType.getName());

        return petType;
    }

    @POST
    @Path("owners/{ownerId}/pets")
    public Response addPetToOwner(@PathParam("ownerId") long ownerId, PetRequest petRequest,  @Context UriInfo uriInfo) {

        LOG.debug("addPetToOwner: " + petRequest.getName());
        
        Pet pet = new Pet();
        Owner owner = ownersService.findById(ownerId);

        owner.addPet(pet);
        
        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

        PetType petType = petsService.findByPetTypeId(petRequest.getTypeId());
        pet.setType(petType);
        LOG.debug("Saving pet: " + pet);
        petsService.save(pet);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();

        return Response.created(uriBuilder.build()).entity(pet).build();
    }  
    
    @PUT
    @Path("owners/{ownerId}/pets/{petId}")
    public Response updatePet(@PathParam("petId") long petId, PetRequest petRequest,  @Context UriInfo uriInfo) {

        LOG.debug("updatePet: " + petRequest.getName());
        
        Pet pet = petsService.update(petId, petRequest);

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();

        return Response.created(uriBuilder.build()).entity(pet).build();
    }  
        
    @GET
    @Path("owners/*/pets/{petId}")
    public PetDetails findPet(@PathParam("petId") long petId) {
        Pet thePet = petsService.findById(petId);
        return new PetDetails(thePet);
    }


}