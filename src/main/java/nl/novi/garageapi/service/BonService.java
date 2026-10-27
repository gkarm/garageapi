//package nl.novi.garageapi.service;
//
//
//import nl.novi.garageapi.dto.BonDto;
//import nl.novi.garageapi.exception.ResourceNotFoundException;
//import nl.novi.garageapi.model.Bon;
//import nl.novi.garageapi.repository.BonRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BonService {
//    @Autowired
//    private BonRepository bonRepository;
//
//    public List<Bon> getAllBons() {
//        return bonRepository.findAll();
//    }
//
//    public Bon getBonById(Long id) {
//        return bonRepository.findById(id).orElse(null);
//    }
//
//    public Bon createBon(BonDto bonDto) {
//
//        Bon newBon = new Bon();
//        newBon.setBedrag(bonDto.getBedrag());
//
//        newBon.setTotaalBedragInclusiefBtw(newBon.getBedrag() * 1.21);
//        newBon.setKeuringBedrag(10.0);
//        newBon.setHandelingenBedrag(20.0);
//        newBon.setOnderdelenBedrag(15.0);
//        return bonRepository.save(newBon);
//    }
//
//    private double berekenTotaalBedrag(Bon bon) {
//        return bon.getKeuringBedrag() + bon.getHandelingenBedrag() + bon.getOnderdelenBedrag();
//    }
//
//
//    public Bon updateBon(Long id, Bon bonDetails) {
//        Bon existingBon = bonRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Bon not found with id " + id));
//
//        existingBon.setKeuringBedrag(bonDetails.getKeuringBedrag());
//        existingBon.setHandelingenBedrag(bonDetails.getHandelingenBedrag());
//        existingBon.setOnderdelenBedrag(bonDetails.getOnderdelenBedrag());
//
//        return bonRepository.save(existingBon);
//    }
//
//
//    public void deleteBon(Long id) {
//        bonRepository.deleteById(id);
//    }
//
//
//}


package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.repository.BonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonService {

    @Autowired
    private BonRepository bonRepository;

    public List<BonDto> getAllBons() {
        List<Bon> bons = bonRepository.findAll();
        List<BonDto> bonDtos = new ArrayList<>();

        for (Bon bon : bons) {
            bonDtos.add(convertToDto(bon));
        }

        return bonDtos;
    }

    public BonDto getBonById(Long id) {
        Bon bon = bonRepository.findById(id).orElse(null);
        if (bon != null) {
            return convertToDto(bon);
        }
        return null;
    }

    public BonDto createBon(BonDto bonDto) {
        Bon newBon = new Bon();
        newBon.setBedrag(bonDto.getBedrag());
        newBon.setKeuringBedrag(bonDto.getKeuringBedrag());
        newBon.setHandelingenBedrag(bonDto.getHandelingenBedrag());
        newBon.setOnderdelenBedrag(bonDto.getOnderdelenBedrag());

        newBon.setTotaalBedragInclusiefBtw(berekenTotaalBedrag(newBon) * 1.21);

        Bon savedBon = bonRepository.save(newBon);
        return convertToDto(savedBon);
    }

    public BonDto updateBon(Long id, BonDto bonDto) {
        Bon existingBon = bonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bon not found with id " + id));

        existingBon.setBedrag(bonDto.getBedrag());
        existingBon.setKeuringBedrag(bonDto.getKeuringBedrag());
        existingBon.setHandelingenBedrag(bonDto.getHandelingenBedrag());
        existingBon.setOnderdelenBedrag(bonDto.getOnderdelenBedrag());

        existingBon.setTotaalBedragInclusiefBtw(berekenTotaalBedrag(existingBon) * 1.21);

        Bon updatedBon = bonRepository.save(existingBon);
        return convertToDto(updatedBon);
    }

    public void deleteBon(Long id) {
        bonRepository.deleteById(id);
    }

    private double berekenTotaalBedrag(Bon bon) {
        return bon.getKeuringBedrag() + bon.getHandelingenBedrag() + bon.getOnderdelenBedrag();
    }

    private BonDto convertToDto(Bon bon) {
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


