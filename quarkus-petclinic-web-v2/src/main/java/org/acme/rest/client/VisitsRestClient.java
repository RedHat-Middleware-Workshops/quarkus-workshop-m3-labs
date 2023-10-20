package org.acme.rest.client;

import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.acme.model.Visit;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient
public interface VisitsRestClient {

    @GET
    @Path("pets/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Visit> visitsMultiGet(@QueryParam("petIds") List<Long> petIds);

    @GET
    @Path("owners/*/pets/{petId}/visits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Visit> visits(@PathParam("petId") long petId);

    @POST
    @Path("owners/*/pets/{petId}/visits")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@PathParam("petId") long petId, Visit theVisit);

}