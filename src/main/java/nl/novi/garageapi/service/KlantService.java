package nl.novi.garageapi.service;




import nl.novi.garageapi.dto.KlantDto;
import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.repository.KlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlantService {

    @Autowired

    private final KlantRepository repos;

    public KlantService(KlantRepository repos) {
        this.repos = repos;
    }

    public List<Klant> getAllKlanten() {
        return repos.findAll();
    }

    public Klant getKlantById(Long id) {
        return repos.findById(id).orElse(null);
    }

    public KlantDto createKlant(KlantDto klantDto) {
        Klant klant = new Klant();
        klant.setFirstName(klantDto.getFirstName());
        klant.setLastName(klantDto.getLastName());
        klant.setPhone(klantDto.getPhone());
        klant.setDob(klantDto.getDob());
        repos.save(klant);
        klantDto.setId(klant.getId());

        return klantDto;
    }

    public Klant updateKlant(Long id, Klant klant) {
        if (repos.existsById(id)) {
            klant.setId(id);
            return repos.save(klant);
        }
        return null;
    }

    public void deleteKlant(Long id) {
        repos.deleteById(id);
    }

}