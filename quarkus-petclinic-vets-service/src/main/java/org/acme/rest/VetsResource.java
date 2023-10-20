package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.service.VetsService;
import org.jboss.logging.Logger;

// import java.util.List;
// import jakarta.ws.rs.GET;
// import org.acme.model.Vet;

@Path("/vets")
@Produces(MediaType.APPLICATION_JSON)
public class VetsResource {

    static Logger LOG = Logger.getLogger(VetsResource.class);

    @Inject
    VetsService service;

    // TODO: Add the List method to retrieve the vets

}