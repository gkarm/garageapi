//package nl.novi.garageapi.controller;
//
//
//import nl.novi.garageapi.model.Onderdeel;
//import nl.novi.garageapi.service.OnderdeelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/onderdelen")
//public class OnderdeelController {
//    private final OnderdeelService service;
//
//    @Autowired
//    public OnderdeelController(OnderdeelService onderdeelService) {
//        this.service = onderdeelService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Onderdeel>> getAllOnderdelen() {
//        List<Onderdeel> onderdelen = service.getAllOnderdelen();
//        return new ResponseEntity<>(onderdelen, HttpStatus.OK);
//
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Onderdeel> getOnderdeelById(@PathVariable Long id) {
//        Onderdeel onderdeel = service.getOnderdeelById(id);
//        if (onderdeel != null) {
//            return new ResponseEntity<>(onderdeel, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<Onderdeel> addOnderdeel(@RequestBody Onderdeel onderdeel) {
//        Onderdeel addedOnderdeel = service.addOnderdeel(onderdeel);
//        return new ResponseEntity<>(addedOnderdeel, HttpStatus.CREATED);
//    }
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Onderdeel> updateOnderdeel(@PathVariable Long id, @RequestBody Onderdeel updatedOnderdeel) {
//        Onderdeel onderdeel = service.updateOnderdeel(id, updatedOnderdeel);
//        if (onderdeel != null) {
//            return new ResponseEntity<>(onderdeel, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
//        service.deleteOnderdeel(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//}

package nl.novi.garageapi.controller;

import nl.novi.garageapi.dto.OnderdeelDto;
import nl.novi.garageapi.model.Onderdeel;
import nl.novi.garageapi.service.OnderdeelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/onderdelen")
public class OnderdeelController {

    private final OnderdeelService service;

    @Autowired
    public OnderdeelController(OnderdeelService onderdeelService) {
        this.service = onderdeelService;
    }

    // --- Helper methods for conversion ---
    private OnderdeelDto toDto(Onderdeel entity) {
        OnderdeelDto dto = new OnderdeelDto();
        dto.setId(entity.getId());
        dto.setOnderdeelName(entity.getOnderdeelName());
        dto.setPrijs(entity.getPrijs());
        dto.setVoorraad(entity.getVoorraad());
        return dto;
    }

    private Onderdeel toEntity(OnderdeelDto dto) {
        Onderdeel entity = new Onderdeel();
        entity.setOnderdeelName(dto.getOnderdeelName());
        entity.setPrijs(dto.getPrijs());
        entity.setVoorraad(dto.getVoorraad());
        // id is not set here because it's either generated (POST) or set separately for PUT
        return entity;
    }

    // --- Endpoints ---

    @GetMapping
    public ResponseEntity<List<OnderdeelDto>> getAllOnderdelen() {
        List<Onderdeel> entities = service.getAllOnderdelen();
        List<OnderdeelDto> dtos = entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OnderdeelDto> getOnderdeelById(@PathVariable Long id) {
        Onderdeel entity = service.getOnderdeelById(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(toDto(entity), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OnderdeelDto> addOnderdeel(@RequestBody OnderdeelDto dto) {
        // Convert DTO to entity (without id)
        Onderdeel entity = toEntity(dto);
        // Save via service
        Onderdeel savedEntity = service.addOnderdeel(entity);
        // Convert saved entity (with generated id) to DTO and return
        return new ResponseEntity<>(toDto(savedEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnderdeelDto> updateOnderdeel(@PathVariable Long id, @RequestBody OnderdeelDto dto) {
        // Check if entity exists
        if (service.getOnderdeelById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Convert DTO to entity and set the ID from path
        Onderdeel entity = toEntity(dto);
        entity.setId(id);
        // Perform update via service
        Onderdeel updatedEntity = service.updateOnderdeel(id, entity);
        return new ResponseEntity<>(toDto(updatedEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
        service.deleteOnderdeel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
