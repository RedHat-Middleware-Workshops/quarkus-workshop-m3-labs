package org.acme.rest;

import java.net.URI;
import java.util.Arrays;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import org.acme.model.Owner;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.acme.model.OwnerForm;

import org.acme.service.OwnersService;

import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/")
public class OwnersResource {

    // TODO: Inject CDI Bean
    @Inject
    OwnersService service;

    @Inject
    Template owners;

    // TODO: Inject editOwner template
    @Inject
    Template editOwner;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("owners")
    public TemplateInstance findOwners(@QueryParam("id") Long id) {
        return owners.data("active", "owners")
                    .data("owners", ((id == null) ? id : Arrays.asList(service.findById(id))));
    }

    // TODO: Add to search an existing owner by last name
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("find")
    public TemplateInstance findByLastName(@QueryParam("lastName") String lastName) {
        return owners.data("active", "owners")
                    .data("lastName", lastName)
                    .data("owners", service.findByLastName(lastName));

    }

    // TODO: Add to Post transaction for adding a new owner
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA) 
    @Transactional 
    @Path("addOwner")
    public Response addOwner(@MultipartForm OwnerForm ownerForm) { 

        Owner newOwner = ownerForm.addOwner();
        newOwner.persist(); 
        return Response.status(Status.MOVED_PERMANENTLY)
                    .location(URI.create("/owners?id=" + newOwner.getId()))
                    .build();
    }

    // TODO: Add to retrieve an existing owner
    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("getOwner")
    public TemplateInstance editOwner(@QueryParam("ownerId") Long ownerId) {

        return editOwner.data("active", "owners")
                        .data("owner", ((ownerId == null) ? "new" : service.findById(ownerId)));
    }
    
    // TODO: Add to Post transaction for editing an existing owner
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Path("editOwner")
    public Response editOwner(@MultipartForm OwnerForm ownerForm, @QueryParam("ownerId") Long ownerId) {

        Owner existingOwner = service.findById(ownerId);
        existingOwner = ownerForm.editOwner(existingOwner);
        return Response.status(Status.MOVED_PERMANENTLY)
                    .location(URI.create("/owners?id=" + ownerId))
                    .build();
    }
}