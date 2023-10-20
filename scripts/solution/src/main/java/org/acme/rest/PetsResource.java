package org.acme.rest;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import org.acme.model.Pet;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import org.acme.service.OwnersService;
import org.acme.model.PetForm;
import org.acme.model.PetType;
import org.acme.service.PetsService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/")
public class PetsResource {

    @Inject 
    OwnersService ownerService;

    @Inject 
    PetsService petService;

    @Inject 
    Template pet;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("getPet") 
    public TemplateInstance getPet(@QueryParam("ownerId") Long ownerId, @QueryParam("petId") Long petId) {
        return pet.data("active", "owners")
                    .data("owner", ownerService.findById(ownerId))
                    .data("pet", (petId != null ? petService.findById(petId) : petId));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Path("addPet") 
    public Response addPet(@MultipartForm PetForm petForm, @QueryParam("ownerId") Long ownerId) {

        Pet newPet = petForm.addPet();
        newPet.setOwner(ownerService.findById(ownerId));
        newPet.setPetType(PetType.findByName(petForm.type));
        newPet.persist();
        return Response.status(Status.MOVED_PERMANENTLY)
                    .location(URI.create("/owners?id=" + ownerId))
                    .build();
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Path("editPet") 
    public Response editOwner(@MultipartForm PetForm petForm, @QueryParam("ownerId") Long ownerId, @QueryParam("petId")Long petId) {

        Pet existingPet = petService.findById(petId);
        existingPet = petForm.editPet(existingPet);
        existingPet.setOwner(ownerService.findById(ownerId));
        existingPet.setPetType(PetType.findByName(petForm.type));
        return Response.status(Status.MOVED_PERMANENTLY)
            .location(URI.create("/owners?id=" + ownerId))
            .build();
    }

}