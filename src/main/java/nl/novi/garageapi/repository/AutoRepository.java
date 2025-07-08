package nl.novi.garageapi.repository;

import nl.novi.garageapi.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoRepository extends JpaRepository<Auto, Long> {

}