package io.pivotal.petclinic.vet;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = { "test" })
public class VetRepositoryTest {

    @Autowired
    private VetRepository vetRepository;

    @Test
    @Ignore("Works locally but not on CI server.  Will troubleshoot later")
    public void testVet() {
        Vet vet = Vet.newVet("Frank", "Smith", Specialty.Radiology, Specialty.Surgey);

        Vet savedVet = vetRepository.save(vet);
        Vet vet1 = vetRepository.findById(savedVet.getId()).get();
        assertThat(vet1.getNrOfSpecialties()).isEqualTo(2);
    }
}
