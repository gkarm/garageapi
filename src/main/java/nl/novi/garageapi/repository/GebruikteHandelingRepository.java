package nl.novi.garageapi.repository;


import nl.novi.garageapi.model.GebruikteHandeling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GebruikteHandelingRepository extends JpaRepository<GebruikteHandeling, Long> {
}