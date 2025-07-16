package nl.novi.garageapi.controller;


import nl.novi.garageapi.model.Onderdeel;
import nl.novi.garageapi.service.OnderdeelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onderdelen")
public class OnderdeelController {
    private final OnderdeelService service;

    @Autowired
    public OnderdeelController(OnderdeelService onderdeelService) {
        this.service = onderdeelService;
    }

    @GetMapping
    public ResponseEntity<List<Onderdeel>> getAllOnderdelen() {
        List<Onderdeel> onderdelen = service.getAllOnderdelen();
        return new ResponseEntity<>(onderdelen, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Onderdeel> getOnderdeelById(@PathVariable Long id) {
        Onderdeel onderdeel = service.getOnderdeelById(id);
        if (onderdeel != null) {
            return new ResponseEntity<>(onderdeel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Onderdeel> addOnderdeel(@RequestBody Onderdeel onderdeel) {
        Onderdeel addedOnderdeel = service.addOnderdeel(onderdeel);
        return new ResponseEntity<>(addedOnderdeel, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Onderdeel> updateOnderdeel(@PathVariable Long id, @RequestBody Onderdeel updatedOnderdeel) {
        Onderdeel onderdeel = service.updateOnderdeel(id, updatedOnderdeel);
        if (onderdeel != null) {
            return new ResponseEntity<>(onderdeel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
        service.deleteOnderdeel(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}