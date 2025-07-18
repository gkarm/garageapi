package nl.novi.garageapi.repository;


import nl.novi.garageapi.model.Reparatie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReparatieRepository extends JpaRepository<Reparatie, Long> {

}