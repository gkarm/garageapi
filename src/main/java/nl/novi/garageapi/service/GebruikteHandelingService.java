package nl.novi.garageapi.service;


import nl.novi.garageapi.model.GebruikteHandeling;
import nl.novi.garageapi.model.Handeling;
import nl.novi.garageapi.model.Reparatie;
import nl.novi.garageapi.repository.GebruikteHandelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GebruikteHandelingService {
    @Autowired
    private GebruikteHandelingRepository gebruikteHandelingRepository;

    public GebruikteHandeling createGebruikteHandeling(Reparatie reparatie, Handeling handeling) {
        GebruikteHandeling gebruikteHandeling = new GebruikteHandeling();
        gebruikteHandeling.setReparatie(reparatie);
        gebruikteHandeling.setHandeling(handeling);
        return gebruikteHandelingRepository.save(gebruikteHandeling);
    }

    public GebruikteHandeling getGebruikteHandelingById(Long id) {
        return gebruikteHandelingRepository.findById(id).orElse(null);
    }

    public void deleteGebruikteHandeling(Long id) {
        gebruikteHandelingRepository.deleteById(id);
    }
}