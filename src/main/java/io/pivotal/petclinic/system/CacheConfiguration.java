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

package io.pivotal.petclinic.system;

import io.pivotal.petclinic.owner.Owner;
import io.pivotal.petclinic.owner.OwnerRepository;
import io.pivotal.petclinic.owner.Pet;
import io.pivotal.petclinic.owner.PetRepository;
import io.pivotal.petclinic.vet.Vet;
import io.pivotal.petclinic.vet.VetRepository;
import io.pivotal.petclinic.visit.Visit;
import io.pivotal.petclinic.visit.VisitRepository;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnableLogging;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.geode.config.annotation.UseMemberName;

@EnableEntityDefinedRegions(
    basePackageClasses = {
        Vet.class,
        Owner.class,
        Pet.class,
        Visit.class
    })
@EnableGemfireRepositories(basePackageClasses = {
    VetRepository.class,
    OwnerRepository.class,
    PetRepository.class,
    VisitRepository.class
})
@EnableLogging(logLevel = "info")
@UseMemberName("PccApiClient")
@Configuration
@Profile({"!test"})
class CacheConfiguration {


}
