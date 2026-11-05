package nl.novi.garageapi.controller;

import nl.novi.garageapi.dto.ReparatieDto;
import nl.novi.garageapi.service.ReparatieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparaties")
public class ReparatieController {

    private final ReparatieService service;

    @Autowired
    public ReparatieController(ReparatieService reparatieService) {
        this.service = reparatieService;
    }

    @GetMapping
    public ResponseEntity<List<ReparatieDto>> getAllReparaties() {
        List<ReparatieDto> reparaties = service.getAllReparaties();
        return new ResponseEntity<>(reparaties, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReparatieDto> getReparatieById(@PathVariable Long id) {
        ReparatieDto reparatie = service.getReparatieById(id);
        if (reparatie != null) {
            return new ResponseEntity<>(reparatie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ReparatieDto> addReparatie(@RequestBody ReparatieDto reparatieDto) {
        ReparatieDto addedReparatie = service.addReparatie(reparatieDto);
        return new ResponseEntity<>(addedReparatie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReparatieDto> updateReparatie(@PathVariable Long id, @RequestBody ReparatieDto reparatieDto) {
        ReparatieDto reparatie = service.updateReparatie(id, reparatieDto);
        if (reparatie != null) {
            return new ResponseEntity<>(reparatie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparatie(@PathVariable Long id) {
        service.deleteReparatie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/niet-uitvoeren")
    public ResponseEntity<Void> setReparatieNietUitvoeren(@PathVariable Long id) {
        service.setReparatieNietUitvoeren(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reparatieId}/toevoegen")
    public ResponseEntity<Void> voegOnderdeelEnHandelingToeAanReparatie(@PathVariable Long reparatieId,
                                                                        @RequestParam Long onderdeelId,
                                                                        @RequestParam Long handelingId) {
        service.voegOnderdeelEnHandelingToeAanReparatie(reparatieId, onderdeelId, handelingId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reparatieId}/koppel-bon")
    public ResponseEntity<Void> koppelBonAanReparatie(@PathVariable Long reparatieId, @RequestParam Long bonId) {
        service.koppelBonAanReparatie(reparatieId, bonId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reparatieId}/toevoegen-onderdeel")
    public ResponseEntity<Void> voegGebruiktOnderdeelToeAanReparatie(@PathVariable Long reparatieId,
                                                                     @RequestParam Long onderdeelId) {
        service.voegGebruiktOnderdeelToeAanReparatie(reparatieId, onderdeelId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{reparatieId}/toevoegen-handeling")
    public ResponseEntity<Void> voegGebruikteHandelingToeAanReparatie(@PathVariable Long reparatieId,
                                                                      @RequestParam Long handelingId) {
        service.voegGebruikteHandelingToeAanReparatie(reparatieId, handelingId);
        return ResponseEntity.ok().build();
    }
}

