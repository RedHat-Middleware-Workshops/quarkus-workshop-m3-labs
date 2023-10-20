package org.acme.rest;

import java.net.URI;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.model.Visit;
import org.acme.model.VisitForm;
import org.acme.rest.client.VisitsRestClient;
import org.acme.service.OwnersService;
import org.acme.service.PetsService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class VisitsResource {

    static Logger LOG = Logger.getLogger(VisitsResource.class);

    @Inject
    @RestClient
    VisitsRestClient visitsRestClient;

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
    @Path("addVisit")
    public Response addVisit(@MultipartForm VisitForm visitForm, @QueryParam("ownerId") Long ownerId, @QueryParam("petId") Long petId) {

        Visit newVisit = visitForm.addVisit();

        LOG.debug("calling add visit: " + newVisit.description + ", " + newVisit.date);

        Response theResponse = visitsRestClient.create(petId, newVisit);

        if (!theResponse.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            throw new RuntimeException("Error adding visit: " + newVisit);
        } 

        return Response.status(301)
                .location(URI.create("/owners?id=" + ownerId))
                .build();
    }

}