package nl.novi.garageapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "autos")
public class Auto {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private String merk;
    @Getter
    private String model;
    @Getter
    private String kenteken;
    private @Past LocalDate bouwjaar;

    @Getter
    @ManyToMany
    private Set<Monteur> monteurs = new HashSet<>();

    @Getter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Klant klant;

    @Getter
    @OneToMany(mappedBy = "auto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Tekortkoming> tekortkomingen = new HashSet<>();

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    public @Past LocalDate getBouwjaar() {
        return bouwjaar;
    }

    public void setBouwjaar(@Past LocalDate bouwjaar) {
        this.bouwjaar = bouwjaar;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }

    public Collection<Tekortkoming> getTekortkomingen() {
        return tekortkomingen;
    }
    public void setTekortkomingen(Set<Tekortkoming> tekortkomingen) {
        this.tekortkomingen = tekortkomingen;
    }

    public void setId(long l) {

    }
}