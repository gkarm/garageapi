//package nl.novi.garageapi.service;
//
//
//import nl.novi.garageapi.dto.KeuringDto;
//import nl.novi.garageapi.model.Keuring;
//import nl.novi.garageapi.repository.KeuringRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class KeuringService {
//    @Autowired
//    private final KeuringRepository repos;
//
//    public KeuringService(KeuringRepository repos) {
//        this.repos = repos;
//    }
//
//    public List<KeuringDto> getAllKeuringen() {
//        List<Keuring> keuringList = repos.findAll();
//        List<KeuringDto> keuringDtoList = new ArrayList<>();
//        for (Keuring keuring : keuringList) {
//            keuringDtoList.add(keuringDtoFromKeuring(keuring));
//
//        }
//
//        return keuringDtoList;
//    }
//    public Keuring getKeuringById(Long id) {
//        Optional<Keuring> keuringOptional = repos.findById(id);
//        return keuringOptional.orElse(null);
//    }
//
//    public Keuring addKeuring(Keuring keuring) {
//        return repos.save(keuring);
//    }
//
//    public Keuring updateKeuring(Long id, Keuring updatedKeuring) {
//        Optional<Keuring> keuringOptional = repos.findById(id);
//        if (keuringOptional.isPresent()) {
//            updatedKeuring.setId(id);
//            return repos.save(updatedKeuring);
//        }
//        return null;
//    }
//    public KeuringDto keuringDtoFromKeuring(Keuring keuring) {
//        KeuringDto keuringDto = new KeuringDto();
//        keuringDto.setKeuringsResultaat(keuring.getKeuringsResultaat());
//        keuringDto.setMonteur(keuring.getMonteur());
//        keuringDto.setStatus(keuring.getStatus());
//        keuringDto.setDatum(keuring.getDatum());
//        keuringDto.setAuto(keuring.getAuto());
//        keuringDto.setId(keuring.getId());
//        keuringDto.setOpmerking(keuring.getOpmerking());
//
//        return keuringDto;
//
//    }
//    public void deleteKeuring(Long id) throws Exception {
//        Optional<Keuring> keuringOptional = repos.findById(id);
//        if (keuringOptional.isPresent()) {
//            repos.deleteById(id);
//        } else {
//            throw new Exception("the Keuring corresponding to ID " +id+ "could not be found");
//        }
//
//    }
//}

package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.KeuringDto;
import nl.novi.garageapi.model.Keuring;
import nl.novi.garageapi.repository.KeuringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KeuringService {

    @Autowired
    private final KeuringRepository repos;

    public KeuringService(KeuringRepository repos) {
        this.repos = repos;
    }

    public List<KeuringDto> getAllKeuringen() {
        List<Keuring> keuringList = repos.findAll();
        List<KeuringDto> keuringDtoList = new ArrayList<>();
        for (Keuring keuring : keuringList) {
            keuringDtoList.add(keuringDtoFromKeuring(keuring));
        }
        return keuringDtoList;
    }

    // Return DTO instead of entity
    public KeuringDto getKeuringDtoById(Long id) {
        Optional<Keuring> keuringOptional = repos.findById(id);
        return keuringOptional.map(this::keuringDtoFromKeuring).orElse(null);
    }

    // Keep internal method if needed, but don't expose in controller
    public Keuring getKeuringById(Long id) {
        Optional<Keuring> keuringOptional = repos.findById(id);
        return keuringOptional.orElse(null);
    }

    // Accept and return DTO
    public KeuringDto addKeuring(KeuringDto keuringDto) {
        Keuring keuring = keuringFromKeuringDto(keuringDto);
        Keuring savedKeuring = repos.save(keuring);
        return keuringDtoFromKeuring(savedKeuring);
    }

    // Accept and return DTO
    public KeuringDto updateKeuring(Long id, KeuringDto keuringDto) {
        Optional<Keuring> keuringOptional = repos.findById(id);
        if (keuringOptional.isPresent()) {
            Keuring keuring = keuringFromKeuringDto(keuringDto);
            keuring.setId(id);
            Keuring updatedKeuring = repos.save(keuring);
            return keuringDtoFromKeuring(updatedKeuring);
        }
        return null;
    }

    public void deleteKeuring(Long id) throws Exception {
        Optional<Keuring> keuringOptional = repos.findById(id);
        if (keuringOptional.isPresent()) {
            repos.deleteById(id);
        } else {
            throw new Exception("The Keuring corresponding to ID " + id + " could not be found");
        }
    }

    // Entity -> DTO conversion
    public KeuringDto keuringDtoFromKeuring(Keuring keuring) {
        KeuringDto keuringDto = new KeuringDto();
        keuringDto.setId(keuring.getId());
        keuringDto.setKeuringsResultaat(keuring.getKeuringsResultaat());
        keuringDto.setMonteur(keuring.getMonteur());
        keuringDto.setStatus(keuring.getStatus());
        keuringDto.setDatum(keuring.getDatum());
        keuringDto.setAuto(keuring.getAuto());
        keuringDto.setOpmerking(keuring.getOpmerking());
        return keuringDto;
    }

    // DTO -> Entity conversion (NEW)
    public Keuring keuringFromKeuringDto(KeuringDto keuringDto) {
        Keuring keuring = new Keuring();
        keuring.setKeuringsResultaat(keuringDto.getKeuringsResultaat());
        keuring.setMonteur(keuringDto.getMonteur());
        keuring.setStatus(keuringDto.getStatus());
        keuring.setDatum(keuringDto.getDatum());
        keuring.setAuto(keuringDto.getAuto());
        keuring.setOpmerking(keuringDto.getOpmerking());
        // ID is typically not set for new entities, handled separately for updates
        return keuring;
    }
}