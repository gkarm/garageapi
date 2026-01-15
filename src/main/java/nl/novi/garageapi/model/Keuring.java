package nl.novi.garageapi.model;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "keuringen")
public class Keuring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "monteur_id")
    private Monteur monteur;

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    @OneToMany(mappedBy = "keuring", cascade = CascadeType.ALL)
    private List<Tekortkoming> tekortkomingen;

    private Date datum;

    @Column(columnDefinition = "TEXT")
    private String opmerking;
    private String status;
    @Column(name = "keurings_resultaat", columnDefinition = "TEXT")
    private String keuringsResultaat;

    public Keuring() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Monteur getMonteur() {
        return monteur;
    }

    public void setMonteur(Monteur monteur) {
        this.monteur = monteur;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getOpmerking() {
        return opmerking;
    }

    public void setOpmerking(String opmerking) {
        this.opmerking = opmerking;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeuringsResultaat() {
        return keuringsResultaat;
    }

    public void setKeuringsResultaat(String keuringsResultaat) {
        this.keuringsResultaat = keuringsResultaat;
    }
}