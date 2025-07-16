package nl.novi.garageapi.dto;


import lombok.Getter;

@Getter
public class GebruikteHandelingDto {
    public Long id;
    public Long reparatieId;
    public Long handelingId;

    public void setId(Long id) {
        this.id = id;
    }

    public void setReparatieId(Long reparatieId) {
        this.reparatieId = reparatieId;
    }

    public void setHandelingId(Long handelingId) {
        this.handelingId = handelingId;
    }
}