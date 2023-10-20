package org.acme.model;

import java.util.List;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

public class Owner  {

    public Long id;

	@NotEmpty
	public String firstName;

	@NotEmpty
	public String lastName;
    
    public String address;
    public String city;

    @NotEmpty
	@Digits(fraction = 0, integer = 10)
    public String telephone;

    public List<Pet> pets;
    
    public Long getId(){
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}