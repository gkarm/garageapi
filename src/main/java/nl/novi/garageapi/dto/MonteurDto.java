package nl.novi.garageapi.dto;

//public class MonteurDto {
//}


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class MonteurDto {

    public Long id;

    @NotBlank             // Validation using springboot starter validation
    public String firstName;

    @Size(min = 3, max = 128)
    public String lastName;
    @Past        // (message = "mag niet in verleden")
    public LocalDate dob;
}