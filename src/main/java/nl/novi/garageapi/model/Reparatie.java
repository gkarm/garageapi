package nl.novi.garageapi.model;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reparaties")
public class Reparatie {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Getter
    @ManyToOne
    private Monteur monteur;
    @Getter
    private Date datum;
    @Getter
    @OneToMany
    private List<Onderdeel> onderdelen;

    @Getter
    private String beschrijving;
    @Getter
    private double totaalBedrag;
    @Getter
    private String status;
    @OneToOne
    private Bon bon;

    @ManyToOne
    private Auto auto;

    @OneToMany(mappedBy = "reparatie", cascade = CascadeType.ALL)
    private List<GebruiktOnderdeel> gebruikteOnderdelen;

    @OneToMany(mappedBy = "reparatie", cascade = CascadeType.ALL)
    private List<GebruikteHandeling> gebruikteHandelingen;

    public List<GebruiktOnderdeel> getGebruikteOnderdelen() {
        return gebruikteOnderdelen;
    }

    public void setGebruikteOnderdelen(List<GebruiktOnderdeel> gebruikteOnderdelen) {
        this.gebruikteOnderdelen = gebruikteOnderdelen;
    }

    public List<GebruikteHandeling> getGebruikteHandelingen() {
        return gebruikteHandelingen;
    }

    public void setGebruikteHandelingen(List<GebruikteHandeling> gebruikteHandelingen) {
        this.gebruikteHandelingen = gebruikteHandelingen;
    }

    public Long getId() {
        return id;
    }

    public Monteur getMonteur() {
        return monteur;
    }

    public Date getDatum() {
        return datum;
    }

    public List<Onderdeel> getOnderdelen() {
        return onderdelen;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getTotaalBedrag() {
        return totaalBedrag;
    }

    public String getStatus() {
        return status;
    }

    public Bon getBon() {
        return bon;
    }

    public void setBon(Bon bon) {
        this.bon = bon;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonteur(Monteur monteur) {
        this.monteur = monteur;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public void setOnderdelen(List<Onderdeel> onderdelen) {
        this.onderdelen = onderdelen;
    }


}