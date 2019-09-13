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
package io.pivotal.petclinic.owner;

import io.pivotal.petclinic.model.NamedEntity;
import io.pivotal.petclinic.vet.Vet;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Region("pets")
public class Pet extends NamedEntity {

    @Getter
    @Setter
    private String name;

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

    public static Pet newPet(String ownerId, String name, LocalDate birthDate, PetType type) {
        Pet pet = new Pet();
        pet.setId(UUID.randomUUID().toString());
        pet.setName(name);
        pet.setBirthDate(birthDate);
        pet.setType(type);
        pet.setOwnerId(ownerId);
        return pet;
    }
}
