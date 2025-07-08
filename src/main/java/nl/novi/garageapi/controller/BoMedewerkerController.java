package nl.novi.garageapi.controller;



import jakarta.validation.Valid;
import nl.novi.garageapi.dto.BoMedewerkerDto;
import nl.novi.garageapi.model.BoMedewerker;
import nl.novi.garageapi.service.BoMedewerkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bomedewerkers")
public class BoMedewerkerController {

    @Autowired

    private final BoMedewerkerService service;

    public BoMedewerkerController(BoMedewerkerService service) {
        this.service = service;
    }
    @GetMapping
    public List<BoMedewerker> getAllBoMedewerkers() {
        return service.getAllBoMedewerkers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoMedewerker> getBoMedewerkerById(@PathVariable Long id) {
        BoMedewerker boMedewerker = service.getBoMedewerkerById(id);
        if (boMedewerker != null) {
            return ResponseEntity.ok(boMedewerker);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<Object> createBoMedewerker(@Valid @RequestBody BoMedewerkerDto boMedewerkerDto, BindingResult br) {
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
            boMedewerkerDto = service.createBoMedewerker(boMedewerkerDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + boMedewerkerDto.id).toUriString());
            return ResponseEntity.created(uri).body(boMedewerkerDto);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<BoMedewerker> updateBoMedewerker(@PathVariable Long id, @RequestBody BoMedewerker boMedewerker) {
        BoMedewerker updatedBoMedewerker = service.updateBoMedewerker(id, boMedewerker);
        if (updatedBoMedewerker != null) {
            return ResponseEntity.ok(updatedBoMedewerker);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoMedewerker(@PathVariable Long id) {
        service.deleteBoMedewerker(id);
        return ResponseEntity.noContent().build();
    }
}