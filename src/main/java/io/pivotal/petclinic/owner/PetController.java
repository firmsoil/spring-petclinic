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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final PetRepository petRepository;
    private final OwnerRepository owners;

    public PetController(PetRepository petRepository, OwnerRepository owners) {
        this.petRepository = petRepository;
        this.owners = owners;
    }

    @ModelAttribute("types")
    public List<String> populatePetTypes() {

        List<String> petTypes = new ArrayList<>();
        for (PetType petType : PetType.values()) {
            petTypes.add(petType.name());
        }
        return petTypes;

    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable("ownerId") String ownerId) {
        return this.owners.findById(ownerId).get();
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, ModelMap model) {
        Pet pet = new Pet();
        pet.setOwnerId(owner.getId());
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model) {
        boolean found = false;
        for(Pet p: petRepository.findByOwnerId(owner.getId())) {
            if(p.getName().equals(pet.getName())) {
                found = true;
            }

        }
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && !found){
            result.rejectValue("name", "duplicate", "already exists");
        }
        pet.setOwnerId(owner.getId());
        pet.setId(UUID.randomUUID().toString());
        if (result.hasErrors()) {
            model.put("pet", pet);
            model.put("owner", owner);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            this.petRepository.save(pet);
            return "redirect:/owners/{ownerId}";
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") String petId, ModelMap model) {
        Pet pet = this.petRepository.findById(petId).get();
        model.put("pet", pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model) {
        if (result.hasErrors()) {
            pet.setOwnerId(owner.getId());
            model.put("pet", pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        } else {
            this.petRepository.save(pet);
            return "redirect:/owners/{ownerId}";
        }
    }

}
