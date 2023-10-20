package org.acme.rest;

import java.net.URI;
import java.util.Arrays;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.model.Owner;
import org.acme.model.OwnerForm;
import org.acme.service.OwnersService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class OwnersResource {

    @Inject
    OwnersService service;

    @Inject
    Template owners;

    @Inject
    Template editOwner;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("owners")
    public TemplateInstance findOwners(@QueryParam("id") Long id) {
        return owners.data("active", "owners")
                    .data("owners", ((id == null) ? id : Arrays.asList(service.findById(id))));
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("find")
    public TemplateInstance findByLastName(@QueryParam("lastName") String lastName) {
        return owners.data("active", "owners")
                    .data("lastName", lastName)
                    .data("owners", service.findByLastName(lastName));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("addOwner")
    public Response addOwner(@MultipartForm OwnerForm ownerForm) {

        Owner newOwner = ownerForm.addOwner();
        
        long id = service.createOwner(newOwner);

        return Response.status(301)
                    .location(URI.create("/owners?id=" + id))
                    .build();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("getOwner")
    public TemplateInstance editOwner(@QueryParam("ownerId") Long ownerId) {
        
        return editOwner.data("active", "owners")
                        .data("owner", ((ownerId == null) ? "new" : service.findById(ownerId)));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("editOwner")
    public Response editOwner(@MultipartForm OwnerForm ownerForm, @QueryParam("ownerId") Long ownerId) {

        Owner existingOwner = service.findById(ownerId);
        existingOwner = ownerForm.editOwner(existingOwner);

        service.updateOwner(ownerId, existingOwner);
        
        return Response.status(301)
                    .location(URI.create("/owners?id=" + ownerId))
                    .build();
    }

}