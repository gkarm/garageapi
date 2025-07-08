package nl.novi.garageapi.controller;

import jakarta.validation.Valid;
import nl.novi.garageapi.dto.KassaMedewerkerDto;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.service.KassaMedewerkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/kassamedewerkers")
public class KassaMedewerkerController {
    @Autowired
    private final KassaMedewerkerService service;

    public KassaMedewerkerController(KassaMedewerkerService service) {
        this.service = service;
    }

    @GetMapping
    public List<KassaMedewerker> getAllKassaMedewerkers() {
        return service.getAllKassamedewerkers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<KassaMedewerker> getKassaMedewerkerById(@PathVariable Long id) {
        KassaMedewerker kassaMedewerker = service.getKassamedewerkerById(id);
        if (kassaMedewerker != null) {
            return ResponseEntity.ok(kassaMedewerker);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> createKassaMedewerker(@Valid @RequestBody KassaMedewerkerDto kassaMedewerkerDto, BindingResult br) {
        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");

            }
            return ResponseEntity.badRequest().body(sb.toString());

        }else {
            kassaMedewerkerDto = service.createKassaMedewerker(kassaMedewerkerDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + kassaMedewerkerDto.id).toUriString());

            return ResponseEntity.created(uri).body(kassaMedewerkerDto);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<KassaMedewerker> updateKassaMedewerker(@PathVariable Long id, @RequestBody KassaMedewerker kassaMedewerker) {
        KassaMedewerker updatedKassamedewerker = service.updateKassamedewerker(id, kassaMedewerker);
        if (updatedKassamedewerker != null) {
            return ResponseEntity.ok(updatedKassamedewerker);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKassamedewerker(@PathVariable Long id) {
        service.deleteKassamedewerker(id);
        return ResponseEntity.noContent().build();
    }
}
