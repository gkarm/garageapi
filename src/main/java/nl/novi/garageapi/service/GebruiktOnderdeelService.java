package nl.novi.garageapi.service;

import nl.novi.garageapi.model.GebruiktOnderdeel;
import nl.novi.garageapi.model.Onderdeel;
import nl.novi.garageapi.model.Reparatie;
import nl.novi.garageapi.repository.GebruiktOnderdeelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GebruiktOnderdeelService {
    @Autowired
    private GebruiktOnderdeelRepository gebruiktOnderdeelRepository;
    private GebruiktOnderdeel gebruiktOnderdeel;

    public GebruiktOnderdeel createGebruiktOnderdeel(Reparatie reparatie, Onderdeel onderdeel) {
        return gebruiktOnderdeelRepository.save(gebruiktOnderdeel);
    }

    public GebruiktOnderdeel getGebruiktOnderdeelById(Long id) {
        return gebruiktOnderdeelRepository.findById(id).orElse(null);
    }

    public void deleteGebruiktOnderdeel(Long id) {
        gebruiktOnderdeelRepository.deleteById(id);
    }

    public GebruiktOnderdeel createGebruiktOnderdeel(GebruiktOnderdeel gebruiktOnderdeel) {
        return null;
    }
}
