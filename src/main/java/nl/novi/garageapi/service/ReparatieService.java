package nl.novi.garageapi.service;


import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.*;
import nl.novi.garageapi.repository.GebruiktOnderdeelRepository;
import nl.novi.garageapi.repository.GebruikteHandelingRepository;
import nl.novi.garageapi.repository.ReparatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ReparatieService {
    @Autowired
    private final ReparatieRepository repos;
    @Autowired
    private GebruiktOnderdeelService gebruiktOnderdeelService;
    @Autowired
    private GebruikteHandelingService gebruikteHandelingService;
    @Autowired
    private HandelingService handelingService;
    @Autowired
    private BonService bonService;
    @Autowired
    private OnderdeelService onderdeelService;
    @Autowired
    private GebruiktOnderdeelRepository gebruiktOnderdeelRepository;
    @Autowired
    private GebruikteHandelingRepository gebruikteHandelingRepository;

    @Autowired

    public ReparatieService(ReparatieRepository repos, BonService bonService) {

        this.repos = repos;
        this.bonService = bonService;
    }

    public List<Reparatie> getAllReparaties() {
        return repos.findAll();
    }

    public Reparatie getReparatieById(Long id) {
        return repos.findById(id).orElse(null);
    }


    public Reparatie addReparatie(Reparatie reparatie) {

        Bon bon = bonService.createBon(new BonDto(0L, 00));
        reparatie.setBon(bon);

        return repos.save(reparatie);
    }



    public Reparatie updateReparatie(Long id, Reparatie updatedReparatie) {
        Reparatie existingReparatie = repos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparatie not found with id " + id));


        existingReparatie.setBeschrijving(updatedReparatie.getBeschrijving());
        existingReparatie.setTotaalBedrag(updatedReparatie.getTotaalBedrag()); // Moet nog berekend worden


        if (existingReparatie.getBon() != null) {

            double totaalBedrag = berekenTotaalBedrag(existingReparatie);

            existingReparatie.getBon().setBedrag(totaalBedrag);

            bonService.updateBon(existingReparatie.getBon().getId(), existingReparatie.getBon());
        }


        return repos.save(existingReparatie);
    }


    private double berekenTotaalBedrag(Reparatie reparatie) {


        return 0.0;
    }





    public void deleteReparatie(Long id) {
        repos.deleteById(id);
    }


    public void setReparatieNietUitvoeren(Long reparatieId) {

        if (reparatieId == null) {
            throw new IllegalArgumentException("ReparatieId cannot be null");
        }


        Reparatie reparatie = repos.findById(reparatieId).orElseThrow(() ->
                new ResourceNotFoundException("Reparatie not found with id " + reparatieId)
        );


        reparatie.setStatus("niet uitvoeren");


        BonDto newBon = new BonDto();
        newBon.setBedrag(0.0);
        Bon createdBon = bonService.createBon(newBon);

        if (createdBon == null) {
            throw new RuntimeException("Failed to create a new bon");
        }


        reparatie.setBon(createdBon);


        repos.save(reparatie);
    }


    public void koppelBonAanReparatie(Long reparatieId, Long bonId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
        Bon bon = bonService.getBonById(bonId);

        if (reparatie != null && bon != null) {
            reparatie.setBon(bon);
            repos.save(reparatie);
        }
    }

    public void voegGebruiktOnderdeelToeAanReparatie(Long reparatieId, Long onderdeelId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
        Onderdeel onderdeel = onderdeelService.getOnderdeelById(onderdeelId);

        List<Onderdeel> OnderdeelLijst = reparatie.getOnderdelen();
        OnderdeelLijst.add(onderdeel);
        reparatie.setOnderdelen(OnderdeelLijst);
        repos.save(reparatie);



    }
    public void voegGebruikteHandelingToeAanReparatie(Long reparatieId, Long handelingId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);

        Handeling handeling = handelingService.getHandelingById(handelingId);

        if (reparatie != null && handeling != null) {
            GebruikteHandeling gebruikteHandeling = new GebruikteHandeling();
            gebruikteHandeling.setReparatie(reparatie);
            gebruikteHandeling.setHandeling(handeling);
            gebruikteHandelingRepository.save(gebruikteHandeling);



        }
    }

    public void voegOnderdeelEnHandelingToeAanReparatie(Long reparatieId, Long onderdeelId, Long handelingId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
        Onderdeel onderdeel = onderdeelService.getOnderdeelById(onderdeelId);
        Handeling handeling = handelingService.getHandelingById(handelingId);

        if (reparatie != null && onderdeel != null && handeling != null) {

            gebruiktOnderdeelService.createGebruiktOnderdeel(reparatie, onderdeel);


            gebruikteHandelingService.createGebruikteHandeling(reparatie, handeling);
        }
    }


}