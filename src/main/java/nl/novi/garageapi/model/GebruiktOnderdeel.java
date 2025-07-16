package nl.novi.garageapi.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class  GebruiktOnderdeel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "reparatie_id", nullable = false)
    private Reparatie reparatie;

    @ManyToOne
    @JoinColumn(name = "onderdeel_id", nullable = false)
    private Onderdeel onderdeel;

    public void setId(Long id) {
        this.id = id;
    }

    public void setReparatie(Reparatie reparatie) {
        this.reparatie = reparatie;
    }

    public void setOnderdeel(Onderdeel onderdeel) {
        this.onderdeel = onderdeel;
    }


}
