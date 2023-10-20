package org.acme.rest;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import org.acme.model.Vet;
import org.acme.rest.client.VetsRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("vets")
public class VetsResource {

    static Logger LOG = Logger.getLogger(VetsResource.class);

    @Inject
    @RestClient
    VetsRestClient vetsRestClient;

    @Inject
    Template vets;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {

        LOG.debug("Calling vetsRestClient");
        List<Vet> data = vetsRestClient.getAll();
        LOG.debug("Received data from vetsRestClient: " + data);

        return vets.data("active", "vets")
                .data("vets", data);
    }
}