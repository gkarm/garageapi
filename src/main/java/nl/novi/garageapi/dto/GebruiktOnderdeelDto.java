package nl.novi.garageapi.dto;


import lombok.Getter;

@Getter
public class GebruiktOnderdeelDto {
    private Long id;
    private Long reparatieId;
    private Long onderdeelId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setReparatieId(Long reparatieId) {
        this.reparatieId = reparatieId;
    }

    public void setOnderdeelId(Long onderdeelId) {
        this.onderdeelId = onderdeelId;
    }

    public GebruiktOnderdeelDto(Long id, Long reparatieId, Long onderdeelId) {
        this.id = id;
        this.reparatieId = reparatieId;
        this.onderdeelId = onderdeelId;
    }


}