package io.pivotal.petclinic.owner;

import io.pivotal.petclinic.model.Person;
import io.pivotal.petclinic.visit.Visit;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class DisplayOwner extends Person {

    @NotEmpty
    @Getter
    @Setter
    private String address;

    @NotEmpty
    @Getter
    @Setter
    private String city;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    @Getter
    @Setter
    private String telephone;

    @Getter
    @Setter
    private List<DisplayPet> pets = new ArrayList<>();

    public DisplayOwner(Owner owner) {
        this.setId(owner.getId());
        this.setFirstName(owner.getFirstName());
        this.setLastName(owner.getLastName());
        this.setAddress(owner.getAddress());
        this.setCity(owner.getCity());
        this.setTelephone(owner.getTelephone());
    }

}
