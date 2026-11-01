package nl.novi.garageapi.service;




import nl.novi.garageapi.dto.AdMedewerkerDto;
import nl.novi.garageapi.model.AdMedewerker;
import nl.novi.garageapi.repository.AdMedewerkerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdMedewerkerService {

    private final AdMedewerkerRepository repos;

    public AdMedewerkerService(AdMedewerkerRepository repos) {
        this.repos = repos;
    }

    public List<AdMedewerker> getAllAdMedewerkers() {
        return repos.findAll();
    }

    public AdMedewerker getAdMedewerkerById(Long id) {
        return repos.findById(id).orElse(null);
    }

    public AdMedewerkerDto createAdMedewerker(AdMedewerkerDto adMedewerkerDto) {
        AdMedewerker adMedewerker = new AdMedewerker();
        adMedewerker.setFirstName(adMedewerkerDto.firstName);
        adMedewerker.setLastName(adMedewerkerDto.lastName);
        adMedewerker.setDob(adMedewerkerDto.dob);
        repos.save(adMedewerker);
        adMedewerkerDto.id = adMedewerker.getId();

        return adMedewerkerDto;

    }
    public AdMedewerker updateAdMedewerker(Long id, AdMedewerkerDto dto) {
        AdMedewerker medewerker = repos.findById(id).orElse(null);
        if (medewerker == null) return null;
        medewerker.setFirstName(dto.firstName);
        medewerker.setLastName(dto.lastName);
        medewerker.setDob(dto.dob);
        return repos.save(medewerker);
    }

    public boolean deleteAdMedewerker(Long id) {
        if (!repos.existsById(id)) return false;
        repos.deleteById(id);
        return true;
    }

}