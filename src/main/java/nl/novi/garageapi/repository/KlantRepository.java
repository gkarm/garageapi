package nl.novi.garageapi.repository;


import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.model.Monteur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface KlantRepository extends JpaRepository<Klant, Long> {


}