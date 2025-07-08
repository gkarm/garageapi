package nl.novi.garageapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

public class AutoDto {
    public Long id;
    @NotBlank
    public String merk;
    @NotBlank
    public String model;
    @NotBlank
    public String kenteken;
    @Past
    public LocalDate bouwjaar;

    public List<Long> monteurIds;
    public Long klantId;
}