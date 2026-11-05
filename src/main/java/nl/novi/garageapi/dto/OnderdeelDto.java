package nl.novi.garageapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnderdeelDto {
    private Long id;
    private String onderdeelName;
    private double prijs;
    private int voorraad;
}


