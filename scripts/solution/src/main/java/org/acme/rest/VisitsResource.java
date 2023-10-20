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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import org.acme.service.OwnersService;
import org.acme.model.VisitForm;
import org.acme.model.Visit;
import org.acme.service.PetsService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/")
public class VisitsResource {

    @Inject
    OwnersService ownerService;

    @Inject
    PetsService petService;

    @Inject
    Template visit;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("getVisit")
    public TemplateInstance getPet(@QueryParam("ownerId") Long ownerId, @QueryParam("petId") Long petId) {
        return visit.data("active", "owners")
                    .data("owner", ownerService.findById(ownerId))
                    .data("pet", petService.findById(petId));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    @Path("addVisit")
    public Response addPet(@MultipartForm VisitForm visitForm, @QueryParam("ownerId") Long ownerId, @QueryParam("petId") Long petId) {

        Visit newVisit = visitForm.addVisit();
        newVisit.setPet(petService.findById(petId));
        newVisit.persist();
        return Response.status(Status.MOVED_PERMANENTLY)
                    .location(URI.create("/owners?id=" + ownerId))
                    .build();
    }

}