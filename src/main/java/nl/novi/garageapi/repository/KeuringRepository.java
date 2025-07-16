package nl.novi.garageapi.repository;


import nl.novi.garageapi.model.Keuring;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeuringRepository extends JpaRepository<Keuring, Long> {

    void deleteById(Long id);
}