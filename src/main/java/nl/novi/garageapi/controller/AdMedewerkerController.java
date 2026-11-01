package nl.novi.garageapi.controller;


import jakarta.validation.Valid;
import nl.novi.garageapi.dto.AdMedewerkerDto;
import nl.novi.garageapi.model.AdMedewerker;
import nl.novi.garageapi.model.Auto;
import nl.novi.garageapi.repository.AdMedewerkerRepository;
import nl.novi.garageapi.repository.AutoRepository;
import nl.novi.garageapi.service.AdMedewerkerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admedewerkers")

public class AdMedewerkerController {


    private final AdMedewerkerService service;

    public AdMedewerkerController(AdMedewerkerService service) {
        this.service = service;
    }

    @GetMapping
    public List<AdMedewerker> getAllAdMedewerkers() {
        return service.getAllAdMedewerkers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdMedewerker> getAdMedewerkerById(@PathVariable Long id) {
        AdMedewerker medewerker = service.getAdMedewerkerById(id);
        return medewerker == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(medewerker);
    }


    @PostMapping
    public ResponseEntity<Object> createAdMedewerker(@Valid @RequestBody AdMedewerkerDto adMedewerkerDto, BindingResult br) {
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
            adMedewerkerDto = service.createAdMedewerker(adMedewerkerDto);
            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + adMedewerkerDto.id).toUriString());
            return ResponseEntity.created(uri).body(adMedewerkerDto);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAdMedewerker(@PathVariable Long id, @Valid @RequestBody AdMedewerkerDto dto, BindingResult br) {
        if (br.hasFieldErrors()) return ResponseEntity.badRequest().build();
        AdMedewerker updated = service.updateAdMedewerker(id, dto);
        return updated == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdMedewerker(@PathVariable Long id) {
        return service.deleteAdMedewerker(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private String validationErrors(BindingResult br) {
        StringBuilder sb = new StringBuilder();
        for (FieldError error : br.getFieldErrors()) {
            sb.append(error.getField()).append(" : ").append(error.getDefaultMessage()).append("\n");
        }
        return sb.toString();
    }

}