package nl.novi.garageapi.model;


import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "kassamedewerkers")
public class KassaMedewerker {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name="first_name", length = 128)
    private String firstName;

    @Setter
    @Column(name="last_name", length = 128)
    private String lastName;

    @Setter
    private LocalDate dob;

    @Setter
    @OneToMany(mappedBy = "kassaMedewerker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileDB> files;

    public List<FileDB> getFiles() {
        return files;
    }

    public Long getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }


    public void setId(Long id) {
        this.id = id;
    }
}