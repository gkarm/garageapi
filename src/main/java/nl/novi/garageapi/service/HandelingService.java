package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.HandelingDto;
import nl.novi.garageapi.model.Handeling;
import nl.novi.garageapi.repository.HandelingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class HandelingService {
    @Autowired
    private HandelingRepository handelingRepository;

    public Handeling createHandeling(Handeling handeling) {
        return handelingRepository.save(handeling);
    }

    public Handeling getHandelingById(Long id) {
        return handelingRepository.findById(id).orElse(null);
    }

    public Handeling updateHandeling(Long id, HandelingDto handelingDto) {
        Handeling handeling = handelingRepository.findById(id).orElse(null);
        if (handeling != null) {
            handeling.setNaam(handelingDto.getNaam());
            handeling.setPrijs(handelingDto.getPrijs());
            return handelingRepository.save(handeling);
        }
        return null;
    }

    public void deleteHandeling(Long id) {
        handelingRepository.deleteById(id);
    }
}
