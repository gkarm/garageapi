package nl.novi.garageapi.repository;



import nl.novi.garageapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
