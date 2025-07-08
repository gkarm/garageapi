package nl.novi.garageapi.service;




import nl.novi.garageapi.dto.AdMedewerkerDto;
import nl.novi.garageapi.model.AdMedewerker;
import nl.novi.garageapi.repository.AdMedewerkerRepository;
import org.springframework.stereotype.Service;

@Service
public class AdMedewerkerService {

    private final AdMedewerkerRepository repos;

    public AdMedewerkerService(AdMedewerkerRepository repos) {
        this.repos = repos;
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
}