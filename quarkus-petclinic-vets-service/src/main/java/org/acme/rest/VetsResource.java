package org.acme.rest;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.service.VetsService;
import org.jboss.logging.Logger;

// import java.util.List;
// import javax.ws.rs.GET;
// import org.acme.model.Vet;

@Path("/vets")
@Produces(MediaType.APPLICATION_JSON)
public class VetsResource {

    static Logger LOG = Logger.getLogger(VetsResource.class);

    @Inject
    VetsService service;

    // TODO: Add the List method to retrieve the vets

}