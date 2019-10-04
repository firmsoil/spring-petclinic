package io.pivotal.petclinic.owner;

import io.pivotal.petclinic.model.NamedEntity;
import io.pivotal.petclinic.visit.Visit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class DisplayPet extends NamedEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter
    @Setter
    private LocalDate birthDate;

    @Getter
    @Setter
    private PetType type;

    @Getter
    @Setter
    private String ownerId;

    @Getter
    @Setter
    private List<Visit> visits = new ArrayList<>();

    public DisplayPet(Pet pet) {
        this.setId(pet.getId());
        this.setName(pet.getName());
        this.setBirthDate(pet.getBirthDate());
        this.setOwnerId(pet.getOwnerId());
        this.setType(pet.getType());
    }

}
