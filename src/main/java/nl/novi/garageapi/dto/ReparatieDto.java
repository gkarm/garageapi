package nl.novi.garageapi.dto;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.model.Onderdeel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReparatieDto {



    @Getter
    private Long id;
    @Getter
    private Monteur monteur;
    @Getter
    private Date datum;
    @Getter
    private String beschrijving;
    @Getter
    private double totaalBedrag;
    @Getter
    private String status;


    @Getter
    private Long bonId;
    @Getter
    private List<Long> onderdelenIds;  // IDs van de onderdelen
    private List<Long> gebruiktOnderdeelIds;  // IDs van gebruikte onderdelen
    @Getter
    private List<Long> gebruikteHandelingIds;  // IDs van gebruikte handelingen




    public ReparatieDto(Long id, Monteur monteur, Date datum, String beschrijving, double totaalBedrag, String status, Long bonId, List<Long> onderdelenIds, List<Long> gebruiktOnderdeelIds, List<Long> gebruikteHandelingIds) {
        this.id = id;
        this.monteur = monteur;
        this.datum = datum;
        this.beschrijving = beschrijving;
        this.totaalBedrag = totaalBedrag;
        this.status = status;
        this.bonId = bonId;
        this.onderdelenIds = onderdelenIds;
        this.gebruiktOnderdeelIds = gebruiktOnderdeelIds;
        this.gebruikteHandelingIds = gebruikteHandelingIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonteur(Monteur monteur) {
        this.monteur = monteur;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setTotaalBedrag(double totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setBonId(Long bonId) {
        this.bonId = bonId;
    }

    public void setOnderdelenIds(List<Long> onderdelenIds) {
        this.onderdelenIds = onderdelenIds;
    }

    public List<Long> getGebruikteOnderdeelIds() {
        return gebruiktOnderdeelIds;
    }

    public void setGebruikteOnderdeelIds(List<Long> gebruikteOnderdeelIds) {
        this.gebruiktOnderdeelIds = gebruikteOnderdeelIds;
    }

    public void setGebruikteHandelingIds(List<Long> gebruikteHandelingIds) {
        this.gebruikteHandelingIds = gebruikteHandelingIds;
    }
}