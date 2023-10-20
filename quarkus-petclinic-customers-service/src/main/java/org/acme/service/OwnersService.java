package org.acme.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.acme.model.Owner;
import org.jboss.logging.Logger;

import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class OwnersService {

    static Logger LOG = Logger.getLogger(OwnersService.class);

    public List<Owner> findAll() {
        return Owner.listAll();
    }

    public Owner findOwner(long ownerId) {
        return Owner.findById(ownerId);
    }

    public List<Owner> findByLastName(String lastName) {

        List<Owner> owners = null;

        if (lastName != null && !lastName.isEmpty()) {
            owners = Owner.find("LOWER(lastName) LIKE LOWER(?1) ",
                    Sort.by("firstName"), "%" + lastName + "%").list();
            
        } else {
            owners = Owner.listAll();
        }

        LOG.debug(owners);
        
        return owners;
    }

    public Owner findById(long id) {
        Owner theOwner = Owner.findById(id);

        return theOwner;
    }

    @Transactional
	public void save(Owner theOwner) {
        theOwner.persist();
    }
    
    @Transactional
    public void update(long ownerId, Owner ownerRequest) {

        Owner ownerModel = Owner.findById(ownerId);
      
        if (ownerModel == null) {
            new IllegalArgumentException("Owner "+ ownerId +" not found");
        }

        ownerModel.setFirstName(ownerRequest.getFirstName());
        ownerModel.setLastName(ownerRequest.getLastName());
        ownerModel.setCity(ownerRequest.getCity());
        ownerModel.setAddress(ownerRequest.getAddress());
        ownerModel.setTelephone(ownerRequest.getTelephone());

        ownerModel.persist();
    }
}