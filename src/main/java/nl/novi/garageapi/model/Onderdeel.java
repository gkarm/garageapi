//package nl.novi.garageapi.model;
//
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "onderdelen")
//public class Onderdeel {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//
//
//
//    @Column(name = "onderdeel_name", length = 128)
//    private String OnderdeelName;
//    @Column(name = "prijs")
//    private double prijs;
//    @Column(name = "voorraad")
//    private int voorraad;
//
//
//
//
//
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getOnderdeelName() {
//        return OnderdeelName;
//    }
//
//    public void setOnderdeelName(String onderdeelName) {
//        OnderdeelName = onderdeelName;
//    }
//
//    public double getPrijs() {
//        return prijs;
//    }
//
//    public void setPrijs(double prijs) {
//        this.prijs = prijs;
//    }
//
//    public int getVoorraad() {
//        return voorraad;
//    }
//
//    public void setVoorraad(int voorraad) {
//        this.voorraad = voorraad;
//    }
//}



package nl.novi.garageapi.model;

import jakarta.persistence.*;

@Entity
@Table(name = "onderdelen")
public class Onderdeel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "onderdeel_name", length = 128)
    private String onderdeelName;

    @Column(name = "prijs")
    private double prijs;

    @Column(name = "voorraad")
    private int voorraad;

    @ManyToOne
    @JoinColumn(name = "monteur_id")
    private Monteur monteur;

    @ManyToOne
    @JoinColumn(name = "kassamedewerker_id")
    private KassaMedewerker kassaMedewerker;

    // Constructors (optional)
    public Onderdeel() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnderdeelName() {
        return onderdeelName;
    }

    public void setOnderdeelName(String onderdeelName) {
        this.onderdeelName = onderdeelName;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }

    public Monteur getMonteur() {
        return monteur;
    }

    public void setMonteur(Monteur monteur) {
        this.monteur = monteur;
    }

    public KassaMedewerker getKassaMedewerker() {
        return kassaMedewerker;
    }

    public void setKassaMedewerker(KassaMedewerker kassaMedewerker) {
        this.kassaMedewerker = kassaMedewerker;
    }
}