package nl.novi.garageapi.controller;

import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.service.BonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bons")
public class BonController {
    @Autowired
    private BonService bonService;
    private double Bon;

    @GetMapping
    public List<Bon> getAllBons() {
        return bonService.getAllBons();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bon> getBonById(@PathVariable Long id) {
        Bon bon = bonService.getBonById(id);
        if (bon != null) {
            return ResponseEntity.ok(bon);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Bon> createBon(@RequestBody BonDto bonDto) {
        Bon newBon = new Bon();
        newBon.setBedrag(bonDto.getBedrag());
        Bon createdBon = bonService.createBon(bonDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBon);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bon> updateBon(@PathVariable Long id, @RequestBody Bon bon) {
        Bon updatedBon = bonService.updateBon(id, bon);
        if (updatedBon != null) {
            return ResponseEntity.ok(updatedBon);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBon(@PathVariable Long id) {
        bonService.deleteBon(id);
        return ResponseEntity.noContent().build();
    }
}