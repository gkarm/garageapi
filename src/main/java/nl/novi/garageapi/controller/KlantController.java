package nl.novi.garageapi.controller;


import jakarta.validation.Valid;
import nl.novi.garageapi.dto.KlantDto;
import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.service.KlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/klanten")
public class KlantController {
    @Autowired

    private final KlantService service;

    public KlantController(KlantService service) {
        this.service = service;
    }

    @GetMapping
    public List<Klant> getAllKlanten() {
        return service.getAllKlanten();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Klant> getKlantById(@PathVariable Long id) {
        Klant klant = service.getKlantById(id);
        if (klant != null) {
            return ResponseEntity.ok(klant);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Object> createKlant(@Valid @RequestBody KlantDto klantDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        } else {
            klantDto = service.createKlant(klantDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + klantDto.getId()).toUriString());
            return ResponseEntity.created(uri).body(klantDto);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Klant> updateKlant(@PathVariable Long id, @RequestBody Klant klant) {
        Klant updatedKlant = service.updateKlant(id, klant);
        if (updatedKlant != null) {
            return ResponseEntity.ok(updatedKlant);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlant(@PathVariable Long id) {
        service.deleteKlant(id);
        return ResponseEntity.noContent().build();
    }
}