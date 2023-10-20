package org.acme.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name="visits")
@Cacheable
public class Visit extends PanacheEntityBase {
  
	@Id
    @SequenceGenerator(
            name = "visitsSequence",
            sequenceName = "visits_id_seq",
            allocationSize = 1,
            initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "visitsSequence")
	public Long id;
	
	@Column(name = "pet_id")
	public long petId;

    @Column(name = "visit_date")
	public LocalDate date;

	public String description;

	public Visit() {

	}
	
	public static List<Visit> findByPetId(long petId) {
		return list("petId", petId);
	}

	public static List<Visit> findByMultiPetIds(List<Long> petIds) {
		return list("petId in (?1)", petIds);
	}

	@Override
	public String toString() {
		return "Visits [date=" + date + ", description=" + description + ", petId=" + petId + ", id=" + id + "]";
	}
	
}