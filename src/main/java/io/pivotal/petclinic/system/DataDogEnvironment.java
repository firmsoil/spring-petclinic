package io.pivotal.petclinic.system;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataDogEnvironment {
    private String deploymentType;
}
