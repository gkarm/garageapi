package nl.novi.garageapi.model;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bomedewerkers")
public class BoMedewerker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "first_name", length = 128)
    private String firstName;

    @Setter
    @Column(name = "last_name", length = 128)
    private String LastName;

    @Setter
    private LocalDate dob;


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public LocalDate getDob() {
        return dob;
    }


    public void setId(Long id) {
    }
}