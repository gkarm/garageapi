package nl.novi.garageapi.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Bon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private double bedrag;
    private double totaalBedragInclusiefBtw;
    private double keuringBedrag;
    private double handelingenBedrag;
    private double onderdelenBedrag;


    public Bon(Long id, double bedrag, double totaalBedragInclusiefBtw, double keuringBedrag, double handelingenBedrag, double onderdelenBedrag) {
        this.id = id;
        this.bedrag = bedrag;
        this.totaalBedragInclusiefBtw = totaalBedragInclusiefBtw;
        this.keuringBedrag = keuringBedrag;
        this.handelingenBedrag = handelingenBedrag;
        this.onderdelenBedrag = onderdelenBedrag;
    }

    public Bon() {

    }

    public double getKeuringBedrag() {
        return keuringBedrag;
    }

    public void setKeuringBedrag(double keuringBedrag) {
        this.keuringBedrag = keuringBedrag;
    }

    public double getHandelingenBedrag() {
        return handelingenBedrag;
    }

    public void setHandelingenBedrag(double handelingenBedrag) {
        this.handelingenBedrag = handelingenBedrag;
    }

    public double getOnderdelenBedrag() {
        return onderdelenBedrag;
    }

    public void setOnderdelenBedrag(double onderdelenBedrag) {
        this.onderdelenBedrag = onderdelenBedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public double getTotaalBedragInclusiefBtw() {
        return totaalBedragInclusiefBtw;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag() {
        this.bedrag = bedrag;
    }

    public void setTotaalBedragInclusiefBtw(double totaalBedragInclusiefBtw) {
        this.totaalBedragInclusiefBtw = totaalBedragInclusiefBtw;
    }
}