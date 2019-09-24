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

import io.pivotal.petclinic.model.Person;
import io.pivotal.petclinic.vet.Vet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * Simple JavaBean domain object representing an owner.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 */
@Region("owners")
@ToString
public class Owner extends Person {

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

    public static Owner newOwner(String firstName, String lastName, String address, String city, String telephone) {
        Owner owner = new Owner();
        owner.setId(UUID.randomUUID().toString());
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        return owner;
    }
}
