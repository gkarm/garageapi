package nl.novi.garageapi.repository;


import nl.novi.garageapi.model.BoMedewerker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoMedewerkerRepository extends JpaRepository<BoMedewerker, Long> {

}