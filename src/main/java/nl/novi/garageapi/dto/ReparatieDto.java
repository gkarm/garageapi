package nl.novi.garageapi.dto;

import java.util.Date;
import java.util.List;

public class ReparatieDto {

    private Long id;
    private Date datum;
    private String beschrijving;
    private double totaalBedrag;
    private String status;
    private Long bonId;
    private List<Long> onderdelenIds;
    private List<Long> gebruikteOnderdeelIds;
    private List<Long> gebruikteHandelingIds;

    public ReparatieDto() {
    }

    public ReparatieDto(Long id, Date datum, String beschrijving, double totaalBedrag,
                        String status, Long bonId, List<Long> onderdelenIds,
                        List<Long> gebruiktOnderdeelIds, List<Long> gebruikteHandelingIds) {
        this.id = id;
        this.datum = datum;
        this.beschrijving = beschrijving;
        this.totaalBedrag = totaalBedrag;
        this.status = status;
        this.bonId = bonId;
        this.onderdelenIds = onderdelenIds;
        this.gebruikteOnderdeelIds = gebruiktOnderdeelIds;
        this.gebruikteHandelingIds = gebruikteHandelingIds;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getTotaalBedrag() {
        return totaalBedrag;
    }

    public void setTotaalBedrag(double totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getBonId() {
        return bonId;
    }

    public void setBonId(Long bonId) {
        this.bonId = bonId;
    }

    public List<Long> getOnderdelenIds() {
        return onderdelenIds;
    }

    public void setOnderdelenIds(List<Long> onderdelenIds) {
        this.onderdelenIds = onderdelenIds;
    }

    public List<Long> getGebruikteOnderdeelIds() {
        return gebruikteOnderdeelIds;
    }

    public void setGebruikteOnderdeelIds(List<Long> gebruikteOnderdeelIds) {
        this.gebruikteOnderdeelIds = gebruikteOnderdeelIds;
    }

    public List<Long> getGebruikteHandelingIds() {
        return gebruikteHandelingIds;
    }

    public void setGebruikteHandelingIds(List<Long> gebruikteHandelingIds) {
        this.gebruikteHandelingIds = gebruikteHandelingIds;
    }
}

