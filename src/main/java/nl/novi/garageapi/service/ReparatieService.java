//package nl.novi.garageapi.service;
//
//
//import nl.novi.garageapi.dto.BonDto;
//import nl.novi.garageapi.exception.ResourceNotFoundException;
//import nl.novi.garageapi.model.*;
//import nl.novi.garageapi.repository.GebruiktOnderdeelRepository;
//import nl.novi.garageapi.repository.GebruikteHandelingRepository;
//import nl.novi.garageapi.repository.ReparatieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//
//public class ReparatieService {
//    @Autowired
//    private final ReparatieRepository repos;
//    @Autowired
//    private GebruiktOnderdeelService gebruiktOnderdeelService;
//    @Autowired
//    private GebruikteHandelingService gebruikteHandelingService;
//    @Autowired
//    private HandelingService handelingService;
//    @Autowired
//    private BonService bonService;
//    @Autowired
//    private OnderdeelService onderdeelService;
//    @Autowired
//    private GebruiktOnderdeelRepository gebruiktOnderdeelRepository;
//    @Autowired
//    private GebruikteHandelingRepository gebruikteHandelingRepository;
//
//    @Autowired
//
//    public ReparatieService(ReparatieRepository repos, BonService bonService) {
//
//        this.repos = repos;
//        this.bonService = bonService;
//    }
//
//    public List<Reparatie> getAllReparaties() {
//        return repos.findAll();
//    }
//
//    public Reparatie getReparatieById(Long id) {
//        return repos.findById(id).orElse(null);
//    }
//
//
//    public Reparatie addReparatie(Reparatie reparatie) {
//
//        Bon bon = bonService.createBon(new BonDto(0L, 00));
//        reparatie.setBon(bon);
//
//        return repos.save(reparatie);
//    }
//
//
//
//    public Reparatie updateReparatie(Long id, Reparatie updatedReparatie) {
//        Reparatie existingReparatie = repos.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Reparatie not found with id " + id));
//
//
//        existingReparatie.setBeschrijving(updatedReparatie.getBeschrijving());
//        existingReparatie.setTotaalBedrag(updatedReparatie.getTotaalBedrag()); // Moet nog berekend worden
//
//
//        if (existingReparatie.getBon() != null) {
//
//            double totaalBedrag = berekenTotaalBedrag(existingReparatie);
//
//            existingReparatie.getBon().setBedrag(totaalBedrag);
//
//            bonService.updateBon(existingReparatie.getBon().getId(), existingReparatie.getBon());
//        }
//
//
//        return repos.save(existingReparatie);
//    }
//
//
//    private double berekenTotaalBedrag(Reparatie reparatie) {
//
//
//        return 0.0;
//    }
//
//
//
//
//
//    public void deleteReparatie(Long id) {
//        repos.deleteById(id);
//    }
//
//
//    public void setReparatieNietUitvoeren(Long reparatieId) {
//
//        if (reparatieId == null) {
//            throw new IllegalArgumentException("ReparatieId cannot be null");
//        }
//
//
//        Reparatie reparatie = repos.findById(reparatieId).orElseThrow(() ->
//                new ResourceNotFoundException("Reparatie not found with id " + reparatieId)
//        );
//
//
//        reparatie.setStatus("niet uitvoeren");
//
//
//        BonDto newBon = new BonDto();
//        newBon.setBedrag(0.0);
//        Bon createdBon = bonService.createBon(newBon);
//
//        if (createdBon == null) {
//            throw new RuntimeException("Failed to create a new bon");
//        }
//
//
//        reparatie.setBon(createdBon);
//
//
//        repos.save(reparatie);
//    }
//
//
//    public void koppelBonAanReparatie(Long reparatieId, Long bonId) {
//        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
//        Bon bon = bonService.getBonById(bonId);
//
//        if (reparatie != null && bon != null) {
//            reparatie.setBon(bon);
//            repos.save(reparatie);
//        }
//    }
//
//    public void voegGebruiktOnderdeelToeAanReparatie(Long reparatieId, Long onderdeelId) {
//        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
//        Onderdeel onderdeel = onderdeelService.getOnderdeelById(onderdeelId);
//
//        List<Onderdeel> OnderdeelLijst = reparatie.getOnderdelen();
//        OnderdeelLijst.add(onderdeel);
//        reparatie.setOnderdelen(OnderdeelLijst);
//        repos.save(reparatie);
//
//
//
//    }
//    public void voegGebruikteHandelingToeAanReparatie(Long reparatieId, Long handelingId) {
//        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
//
//        Handeling handeling = handelingService.getHandelingById(handelingId);
//
//        if (reparatie != null && handeling != null) {
//            GebruikteHandeling gebruikteHandeling = new GebruikteHandeling();
//            gebruikteHandeling.setReparatie(reparatie);
//            gebruikteHandeling.setHandeling(handeling);
//            gebruikteHandelingRepository.save(gebruikteHandeling);
//
//
//
//        }
//    }
//
//    public void voegOnderdeelEnHandelingToeAanReparatie(Long reparatieId, Long onderdeelId, Long handelingId) {
//        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
//        Onderdeel onderdeel = onderdeelService.getOnderdeelById(onderdeelId);
//        Handeling handeling = handelingService.getHandelingById(handelingId);
//
//        if (reparatie != null && onderdeel != null && handeling != null) {
//
//            gebruiktOnderdeelService.createGebruiktOnderdeel(reparatie, onderdeel);
//
//
//            gebruikteHandelingService.createGebruikteHandeling(reparatie, handeling);
//        }
//    }
//
//
//}




package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.dto.ReparatieDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.*;
import nl.novi.garageapi.repository.GebruiktOnderdeelRepository;
import nl.novi.garageapi.repository.GebruikteHandelingRepository;
import nl.novi.garageapi.repository.ReparatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ReparatieDto> getAllReparaties() {
        List<Reparatie> reparaties = repos.findAll();
        return reparaties.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReparatieDto getReparatieById(Long id) {
        Reparatie reparatie = repos.findById(id).orElse(null);
        if (reparatie != null) {
            return convertToDto(reparatie);
        }
        return null;
    }

    public ReparatieDto addReparatie(ReparatieDto reparatieDto) {
        Reparatie reparatie = convertToEntity(reparatieDto);

        // Create a new BonDto and convert to Bon entity
        BonDto bonDto = new BonDto(0L, 0.0);
        Bon bon = convertDtoToBon(bonDto);
        reparatie.setBon(bon);

        Reparatie savedReparatie = repos.save(reparatie);
        return convertToDto(savedReparatie);
    }

    public ReparatieDto updateReparatie(Long id, ReparatieDto reparatieDto) {
        Reparatie existingReparatie = repos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reparatie not found with id " + id));

        // Update fields from DTO
        existingReparatie.setBeschrijving(reparatieDto.getBeschrijving());
        existingReparatie.setTotaalBedrag(reparatieDto.getTotaalBedrag());
        existingReparatie.setStatus(reparatieDto.getStatus());

        if (reparatieDto.getDatum() != null) {
            existingReparatie.setDatum(reparatieDto.getDatum());
        }

        if (existingReparatie.getBon() != null) {
            double totaalBedrag = berekenTotaalBedrag(existingReparatie);
            existingReparatie.getBon().setBedrag(totaalBedrag);

            // Convert Bon to BonDto for service call
            BonDto bonDto = convertBonToDto(existingReparatie.getBon());
            bonService.updateBon(existingReparatie.getBon().getId(), bonDto);
        }

        Reparatie updatedReparatie = repos.save(existingReparatie);
        return convertToDto(updatedReparatie);
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

        BonDto newBonDto = new BonDto();
        newBonDto.setBedrag(0.0);
        BonDto createdBonDto = bonService.createBon(newBonDto);

        if (createdBonDto == null) {
            throw new RuntimeException("Failed to create a new bon");
        }

        // Convert Dto back to entity if needed
        Bon createdBon = convertDtoToBon(createdBonDto);
        reparatie.setBon(createdBon);

        repos.save(reparatie);
    }

    public void koppelBonAanReparatie(Long reparatieId, Long bonId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
        BonDto bonDto = bonService.getBonById(bonId);

        if (reparatie != null && bonDto != null) {
            Bon bon = convertDtoToBon(bonDto);
            reparatie.setBon(bon);
            repos.save(reparatie);
        }
    }

    public void voegGebruiktOnderdeelToeAanReparatie(Long reparatieId, Long onderdeelId) {
        Reparatie reparatie = repos.findById(reparatieId).orElse(null);
        Onderdeel onderdeel = onderdeelService.getOnderdeelById(onderdeelId);

        if (reparatie != null && onderdeel != null) {
            List<Onderdeel> onderdeelLijst = reparatie.getOnderdelen();
            if (onderdeelLijst != null) {
                onderdeelLijst.add(onderdeel);
                reparatie.setOnderdelen(onderdeelLijst);
                repos.save(reparatie);
            }
        }
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

    // Helper method to convert Reparatie entity to ReparatieDto
    private ReparatieDto convertToDto(Reparatie reparatie) {
        ReparatieDto dto = new ReparatieDto();
        dto.setId(reparatie.getId());
        dto.setBeschrijving(reparatie.getBeschrijving());
        dto.setDatum(reparatie.getDatum());
        dto.setTotaalBedrag(reparatie.getTotaalBedrag());
        dto.setStatus(reparatie.getStatus());

        if (reparatie.getBon() != null) {
            dto.setBonId(reparatie.getBon().getId());
        }

        if (reparatie.getOnderdelen() != null) {
            dto.setOnderdelenIds(reparatie.getOnderdelen().stream()
                    .map(Onderdeel::getId)
                    .collect(Collectors.toList()));
        }

        if (reparatie.getGebruikteOnderdelen() != null) {
            dto.setGebruikteOnderdeelIds(reparatie.getGebruikteOnderdelen().stream()
                    .map(GebruiktOnderdeel::getId)
                    .collect(Collectors.toList()));
        }

        if (reparatie.getGebruikteHandelingen() != null) {
            dto.setGebruikteHandelingIds(reparatie.getGebruikteHandelingen().stream()
                    .map(GebruikteHandeling::getId)
                    .collect(Collectors.toList()));
        }

        // Note: Monteur mapping would need to be added here if needed
        // dto.setMonteur(reparatie.getMonteur());

        return dto;
    }

    // Helper method to convert ReparatieDto to Reparatie entity
    private Reparatie convertToEntity(ReparatieDto dto) {
        Reparatie reparatie = new Reparatie();
        reparatie.setId(dto.getId());
        reparatie.setBeschrijving(dto.getBeschrijving());
        reparatie.setDatum(dto.getDatum());
        reparatie.setTotaalBedrag(dto.getTotaalBedrag());
        reparatie.setStatus(dto.getStatus());

        // Note: Monteur, Bon, and Onderdelen would typically be fetched from their respective services
        // using the IDs from the DTO, rather than setting them directly

        return reparatie;
    }

    // Helper method to convert BonDto to Bon entity
    private Bon convertDtoToBon(BonDto dto) {
        Bon bon = new Bon();
        bon.setId(dto.getId());
        bon.setBedrag(dto.getBedrag());
        bon.setTotaalBedragInclusiefBtw(dto.getTotaalBedragInclusiefBtw());
        bon.setKeuringBedrag(dto.getKeuringBedrag());
        bon.setHandelingenBedrag(dto.getHandelingenBedrag());
        bon.setOnderdelenBedrag(dto.getOnderdelenBedrag());
        return bon;
    }

    // Helper method to convert Bon entity to BonDto
    private BonDto convertBonToDto(Bon bon) {
        BonDto dto = new BonDto();
        dto.setId(bon.getId());
        dto.setBedrag(bon.getBedrag());
        dto.setTotaalBedragInclusiefBtw(bon.getTotaalBedragInclusiefBtw());
        dto.setKeuringBedrag(bon.getKeuringBedrag());
        dto.setHandelingenBedrag(bon.getHandelingenBedrag());
        dto.setOnderdelenBedrag(bon.getOnderdelenBedrag());
        return dto;
    }
}

