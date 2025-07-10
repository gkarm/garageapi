package nl.novi.garageapi.service;



import nl.novi.garageapi.Security.MyUserDetails;
import nl.novi.garageapi.dto.MonteurDto;
import nl.novi.garageapi.enumeration.UserRole;
import nl.novi.garageapi.exception.ForbiddenException;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.repository.MonteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonteurService {
    @Autowired


    private final MonteurRepository repos;

    public List<Monteur> getAllMonteurs() {
        return repos.findAll();
    }

    public Monteur getMonteurById(Long id) {
        return repos.findById(id).orElse(null);
    }
    public MonteurService(MonteurRepository repos) {
        this.repos = repos;
    }

    public MonteurDto createMonteur(MyUserDetails myUserDetails, MonteurDto monteurDto) {
        if (!myUserDetails.getUserRole().contains(UserRole.ADMIN)) {
            throw new ForbiddenException("You are logged in as "+myUserDetails.getUsername()+" But you are not authorized ");
        }
        Monteur monteur = new Monteur();
        monteur.setFirstName(monteurDto.firstName);
        monteur.setLastName(monteurDto.lastName);
        monteur.setDob(monteurDto.dob);
        repos.save(monteur);
        monteurDto.id = monteur.getId();

        return monteurDto;
    }

    public Monteur updateMonteur(Long id, Monteur monteur) {
        if (repos.existsById(id)) {
            monteur.getId();
            return repos.save(monteur);
        }
        return null;
    }

    public void deleteMonteur(Long id) {
        repos.deleteById(id);
    }
}
