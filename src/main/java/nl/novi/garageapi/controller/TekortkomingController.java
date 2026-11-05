package nl.novi.garageapi.controller;

import nl.novi.garageapi.dto.TekortkomingDto;
import nl.novi.garageapi.service.TekortkomingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tekortkomingen")
public class TekortkomingController {

    private final TekortkomingService tekortkomingService;


    public TekortkomingController(
            TekortkomingService tekortkomingService) {
        this.tekortkomingService = tekortkomingService;
    }

    @GetMapping
    public ResponseEntity<List<TekortkomingDto>> getAllTekortkomingen() {
        return ResponseEntity.ok(
                tekortkomingService.getAllTekortkomingen()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TekortkomingDto> getTekortkomingById(
            @PathVariable Long id) {

        TekortkomingDto tekortkoming =
                tekortkomingService.getTekortkomingById(id);

        if (tekortkoming == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(tekortkoming);
    }

    @PostMapping
    public ResponseEntity<TekortkomingDto> addTekortkomingToAuto(
            @RequestBody TekortkomingDto tekortkomingDto) {

        TekortkomingDto createdTekortkoming =
                tekortkomingService.addTekortkomingToAuto(tekortkomingDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTekortkoming);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TekortkomingDto> updateTekortkoming(
            @PathVariable Long id,
            @RequestBody TekortkomingDto tekortkomingDto) {

        TekortkomingDto updatedTekortkoming =
                tekortkomingService.updateTekortkoming(id, tekortkomingDto);

        if (updatedTekortkoming == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTekortkoming);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTekortkoming(
            @PathVariable Long id) {

        boolean deleted = tekortkomingService.deleteTekortkoming(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

