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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = { "test" })
public class PetControllerTests {

    private static final String TEST_OWNER_ID = "foo";
    private static final String TEST_PET_ID = "bar";


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private OwnerRepository owners;

    @Before
    public void setup() {
        Owner owner = new Owner();
        owner.setId(TEST_OWNER_ID);
        given(this.owners.findById(any())).willReturn(Optional.of(owner));
        Pet betty = new Pet();
        betty.setName("Betty");
        ArrayList<Pet> pets = new ArrayList<>();
        pets.add(betty);
        given(this.petRepository.findByOwnerIdOrderByName(any())).willReturn(pets);
        given(this.petRepository.findById(any())).willReturn(Optional.of(betty));

    }

    @Test
    public void testInitCreationForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/new", TEST_OWNER_ID))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"))
            .andExpect(model().attributeExists("pet"));
    }

    @Test
    public void testProcessCreationFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
            .param("name", "Betty")
            .param("type", "Hamster")
            .param("birthDate", "2015-02-12")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void testProcessCreationFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/new", TEST_OWNER_ID)
            .param("name", "Betty")
            .param("birthDate", "2015-02-12")
        )
            .andExpect(model().attributeHasNoErrors("owner"))
            .andExpect(model().attributeHasErrors("pet"))
            .andExpect(model().attributeHasFieldErrors("pet", "type"))
            .andExpect(model().attributeHasFieldErrorCode("pet", "type", "required"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void testInitUpdateForm() throws Exception {
        mockMvc.perform(get("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("pet"))
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    public void testProcessUpdateFormSuccess() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
            .param("name", "Betty")
            .param("type", "Hamster")
            .param("birthDate", "2015-02-12")
        )
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    public void testProcessUpdateFormHasErrors() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID)
            .param("name", "Betty")
            .param("birthDate", "2015/02/12")
        )
            .andExpect(model().attributeHasNoErrors("owner"))
            .andExpect(model().attributeHasErrors("pet"))
            .andExpect(status().isOk())
            .andExpect(view().name("pets/createOrUpdatePetForm"));
    }

}
