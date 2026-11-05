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

    private final KeuringService service;

    @Autowired

    public KeuringController(KeuringService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<KeuringDto>> getAllKeuringen() {
        List<KeuringDto> keuringen = service.getAllKeuringen();
        return new ResponseEntity<>(keuringen, HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<KeuringDto> getKeuringById(@PathVariable Long id) {
        KeuringDto dto = service.getKeuringDtoById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<Object> addKeuring(@Valid @RequestBody KeuringDto keuringDto, BindingResult br) {
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
            KeuringDto createdDto = service.addKeuring(keuringDto);

            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(createdDto.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(createdDto);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<KeuringDto> updateKeuring(@PathVariable Long id, @Valid @RequestBody KeuringDto keuringDto) {
        KeuringDto updatedDto = service.updateKeuring(id, keuringDto);
        if (updatedDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKeuring(@PathVariable Long id) throws Exception {
        service.deleteKeuring(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}