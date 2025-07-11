package nl.novi.garageapi.repository;

import nl.novi.garageapi.model.Bon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonRepository extends JpaRepository<Bon, Long> {
}
