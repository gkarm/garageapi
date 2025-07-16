package nl.novi.garageapi.controller;


import nl.novi.garageapi.dto.GebruikteHandelingDto;
import nl.novi.garageapi.model.GebruikteHandeling;
import nl.novi.garageapi.model.Handeling;
import nl.novi.garageapi.model.Reparatie;
import nl.novi.garageapi.service.GebruikteHandelingService;
import nl.novi.garageapi.service.HandelingService;
import nl.novi.garageapi.service.ReparatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gebruikte-handelingen")
public class GebruikteHandelingController {

    @Autowired
    private GebruikteHandelingService gebruikteHandelingService;
    @Autowired
    private ReparatieService reparatieService;
    @Autowired
    private HandelingService handelingService;



    @PostMapping
    public ResponseEntity<GebruikteHandeling> createGebruikteHandeling(@RequestBody GebruikteHandelingDto gebruikteHandelingDto) {
        Reparatie reparatie = reparatieService.getReparatieById(gebruikteHandelingDto.getReparatieId());

        Handeling handeling = handelingService.getHandelingById(gebruikteHandelingDto.getHandelingId());

        if (reparatie == null || handeling == null) {
            return ResponseEntity.badRequest().build();
        }

        GebruikteHandeling createdGebruikteHandeling = gebruikteHandelingService.createGebruikteHandeling(reparatie, handeling);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGebruikteHandeling);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GebruikteHandeling> getGebruikteHandelingById(@PathVariable Long id) {
        GebruikteHandeling gebruikteHandeling = gebruikteHandelingService.getGebruikteHandelingById(id);
        if (gebruikteHandeling != null) {
            return ResponseEntity.ok(gebruikteHandeling);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebruikteHandeling(@PathVariable Long id) {
        gebruikteHandelingService.deleteGebruikteHandeling(id);
        return ResponseEntity.noContent().build();
    }

    private GebruikteHandeling mapToGebruikteHandeling(GebruikteHandelingDto gebruikteHandelingDto) {
        GebruikteHandeling gebruikteHandeling = new GebruikteHandeling();
        gebruikteHandeling.setReparatieId(gebruikteHandelingDto.getReparatieId());
        gebruikteHandeling.setHandelingId(gebruikteHandelingDto.getHandelingId());
        return gebruikteHandeling;
    }

}