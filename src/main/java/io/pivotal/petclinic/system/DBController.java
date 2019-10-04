package io.pivotal.petclinic.system;

import io.pivotal.petclinic.owner.*;
import io.pivotal.petclinic.vet.Specialty;
import io.pivotal.petclinic.vet.Vet;
import io.pivotal.petclinic.vet.VetRepository;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Log
@RestController
public class DBController {


    private OwnerRepository ownerRepository;
    private VetRepository vetRepository;
    private PetRepository petRepository;

    public DBController(OwnerRepository ownerRepository,
                        VetRepository vetRepository,
                        PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.vetRepository = vetRepository;
        this.petRepository = petRepository;
    }

    @GetMapping("/loaddb")
    public String loadDB() {
        vetRepository.save(Vet.newVet("James", "Carter"));
        vetRepository.save(Vet.newVet("Helen", "Leary", Specialty.Radiology));
        vetRepository.save(Vet.newVet("Linda", "Douglas", Specialty.Surgey, Specialty.Dentistry));
        vetRepository.save(Vet.newVet("Rafael", "Ortega", Specialty.Surgey));
        vetRepository.save(Vet.newVet("Henry", "Stevens", Specialty.Radiology));
        vetRepository.save(Vet.newVet("Sharon", "Jenkins"));

        Owner george = Owner.newOwner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
        ownerRepository.save(george);

        Pet leo = Pet.newPet(george.getId(), "Leo", LocalDate.parse("2010-09-07"), PetType.Cat);
        petRepository.save(leo);

        Owner betty = Owner.newOwner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");
        ownerRepository.save(betty);

        Pet basil = Pet.newPet(betty.getId(), "Basil", LocalDate.parse("2012-08-06"), PetType.Hamster);
        petRepository.save(basil);

        Owner harold = Owner.newOwner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198");
        ownerRepository.save(harold);

        Pet iggy = Pet.newPet(harold.getId(), "Iggy", LocalDate.parse("2010-11-30"), PetType.Lizard);
        petRepository.save(iggy);

        petRepository.findAll().forEach(pet -> log.info(pet.toString()));
        vetRepository.findAll().forEach(vet -> log.info(vet.toString()));

        return "PCC Loadded";

    }
}
