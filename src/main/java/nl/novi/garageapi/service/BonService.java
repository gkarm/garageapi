package nl.novi.garageapi.service;


import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.repository.BonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BonService {
    @Autowired
    private BonRepository bonRepository;

    public List<Bon> getAllBons() {
        return bonRepository.findAll();
    }

    public Bon getBonById(Long id) {
        return bonRepository.findById(id).orElse(null);
    }

    public Bon createBon(BonDto bonDto) {

        Bon newBon = new Bon();
        newBon.setBedrag(bonDto.getBedrag());

        newBon.setTotaalBedragInclusiefBtw(newBon.getBedrag() * 1.21);
        newBon.setKeuringBedrag(10.0);
        newBon.setHandelingenBedrag(20.0);
        newBon.setOnderdelenBedrag(15.0);
        return bonRepository.save(newBon);
    }

    private double berekenTotaalBedrag(Bon bon) {
        return bon.getKeuringBedrag() + bon.getHandelingenBedrag() + bon.getOnderdelenBedrag();
    }


    public Bon updateBon(Long id, Bon bonDetails) {
        Bon existingBon = bonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bon not found with id " + id));

        existingBon.setKeuringBedrag(bonDetails.getKeuringBedrag());
        existingBon.setHandelingenBedrag(bonDetails.getHandelingenBedrag());
        existingBon.setOnderdelenBedrag(bonDetails.getOnderdelenBedrag());

        return bonRepository.save(existingBon);
    }


    public void deleteBon(Long id) {
        bonRepository.deleteById(id);
    }


}