package org.acme.rest.client;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.model.Vet;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/vets")
@RegisterRestClient
public interface VetsRestClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vet> getAll();

}
