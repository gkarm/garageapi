package nl.novi.garageapi.model;

import jakarta.persistence.*;

@Entity
public class Handeling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naam;
    private double prijs;

    @ManyToOne
    private BoMedewerker boMedewerker;

    public Handeling(Long id, String naam, double prijs) {
        this.id = id;
        this.naam = naam;
        this.prijs = prijs;
    }

    public Handeling() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
}