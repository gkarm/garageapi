package nl.novi.garageapi.controller;


import jakarta.validation.Valid;
import nl.novi.garageapi.dto.KeuringDto;
import nl.novi.garageapi.model.Keuring;
import nl.novi.garageapi.service.KeuringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/keuringen")
public class KeuringController {
    @Autowired
    private final KeuringService service;

    public KeuringController(KeuringService keuringService) {
        this.service = keuringService;
    }

    @GetMapping
    public ResponseEntity<List<KeuringDto>> getAllKeuringen() {
        List<KeuringDto> keuringen = service.getAllKeuringen();
        return new ResponseEntity<>(keuringen, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeuringDto> getKeuringById(@PathVariable Long id) {
        Keuring keuring = service.getKeuringById(id);
        if (keuring == null) {
            return ResponseEntity.notFound().build();
        }
        KeuringDto dto = service.keuringDtoFromKeuring(keuring);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Object> addKeuring(@Valid @RequestBody Keuring keuringDto, BindingResult br) {
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
            keuringDto = service.addKeuring(keuringDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + keuringDto).toUriString());
            return ResponseEntity.created(uri).body(keuringDto);

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeuring(@PathVariable Long id) throws Exception {
        service.deleteKeuring(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}