package org.acme.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import org.acme.model.Visit;
import org.jboss.logging.Logger;

@ApplicationScoped
public class VisitsService {
   
    static Logger LOG = Logger.getLogger(VisitsService.class);

    public List<Visit> findByPetId(Long id) {
        return Visit.findByPetId(id.longValue());
    }

	public List<Visit> getAllVisits() {
		return Visit.listAll();
	}

	public List<Visit> findByMultiPetIds(List<Long> petIds) {
		return Visit.findByMultiPetIds(petIds);
	}

    @Transactional
	public void save(Visit theVisit) {

    	LOG.debug("saving: " + theVisit);
        theVisit.persist();
	}

}
