package nl.novi.garageapi.dto;




import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class AdMedewerkerDto {
    public Long id;
    @NotBlank
    public String firstName;
    @Size(min = 3, max = 128)
    public String lastName;
    @Past
    public LocalDate dob;
}