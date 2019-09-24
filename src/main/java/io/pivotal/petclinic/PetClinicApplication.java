/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.petclinic;

import io.pivotal.petclinic.owner.*;
import io.pivotal.petclinic.vet.Specialty;
import io.pivotal.petclinic.vet.Vet;
import io.pivotal.petclinic.vet.VetRepository;
import io.pivotal.petclinic.visit.VisitRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * PetClinic Spring Boot Application.
 *
 * @author Dave Syer
 */
@SpringBootApplication
@Log
public class PetClinicApplication {

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner run(ApplicationContext appContext) {
//        return args -> {
//
//            String[] beans = appContext.getBeanDefinitionNames();
//            Arrays.stream(beans).sorted().forEach(System.out::println);
//
//        };
//    }

//    @Profile({"!cloud"})
//    @Bean
//    CommandLineRunner loadData(OwnerRepository ownerRepository,
//                               VetRepository vetRepository,
//                               PetRepository petRepository,
//                               VisitRepository visitRepository) {
//        return commandLineRunner -> {
//            vetRepository.save(Vet.newVet("James", "Carter"));
//            vetRepository.save(Vet.newVet("Helen", "Leary", Specialty.Radiology));
//            vetRepository.save(Vet.newVet("Linda", "Douglas", Specialty.Surgey, Specialty.Dentistry));
//            vetRepository.save(Vet.newVet("Rafael", "Ortega", Specialty.Surgey));
//            vetRepository.save(Vet.newVet("Henry", "Stevens", Specialty.Radiology));
//            vetRepository.save(Vet.newVet("Sharon", "Jenkins"));
//
//            Owner george = Owner.newOwner("George", "Franklin", "110 W. Liberty St.", "Madison", "6085551023");
//            ownerRepository.save(george);
//
//            Pet leo = Pet.newPet(george.getId(), "Leo", LocalDate.parse("2010-09-07"), PetType.Cat);
//            petRepository.save(leo);
//
//            Owner betty = Owner.newOwner("Betty", "Davis", "638 Cardinal Ave.", "Sun Prairie", "6085551749");
//            ownerRepository.save(betty);
//
//            Pet basil = Pet.newPet(betty.getId(), "Basil", LocalDate.parse("2012-08-06"), PetType.Hamster);
//            petRepository.save(basil);
//
//            Owner harold = Owner.newOwner("Harold", "Davis", "563 Friendly St.", "Windsor", "6085553198");
//            ownerRepository.save(harold);
//
//            Pet iggy = Pet.newPet(harold.getId(), "Iggy", LocalDate.parse("2010-11-30"), PetType.Lizard);
//            petRepository.save(iggy);
//
//            petRepository.findAll().forEach(pet -> log.info(pet.toString()));
//            vetRepository.findAll().forEach(vet -> log.info(vet.toString()));
//
//        };
//    }

}
