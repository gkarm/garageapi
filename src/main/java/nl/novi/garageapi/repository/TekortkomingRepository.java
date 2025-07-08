package nl.novi.garageapi.repository;



import nl.novi.garageapi.model.Tekortkoming;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TekortkomingRepository extends JpaRepository<Tekortkoming, Long> {

}