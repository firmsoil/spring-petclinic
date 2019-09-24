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
package io.pivotal.petclinic.vet;

import io.pivotal.petclinic.model.Person;
import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Simple JavaBean domain object representing a veterinarian.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Arjen Poutsma
 */
@Region("vets")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vet extends Person {

    @Getter
    @Setter
    private Set<Specialty> specialties;

    @Transient
    public int getNrOfSpecialties() {
        return specialties.size();
    }

    public static Vet newVet(String firstName, String lastName, Specialty... specialties) {
        Vet vet = new Vet();
        vet.setId(UUID.randomUUID().toString());
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vet.setSpecialties(new HashSet<>(Arrays.asList(specialties)));
        return vet;
    }

}
