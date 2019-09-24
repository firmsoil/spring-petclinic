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
package io.pivotal.petclinic.visit;

import io.pivotal.petclinic.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.gemfire.mapping.annotation.Region;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

/**
 * Simple JavaBean domain object representing a visit.
 *
 * @author Ken Krebs
 * @author Dave Syer
 */
@Region("visits")
public class Visit extends BaseEntity {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Getter
    @Setter
    private LocalDate date;

    @NotEmpty
    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String petId;

    /**
     * Creates a new instance of Visit for the current date
     */
    public Visit() {
        this.date = LocalDate.now();
    }

}
