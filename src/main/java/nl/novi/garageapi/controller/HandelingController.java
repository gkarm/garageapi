package nl.novi.garageapi.controller;


import nl.novi.garageapi.dto.HandelingDto;
import nl.novi.garageapi.model.Handeling;
import nl.novi.garageapi.service.HandelingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/handelingen")
public class HandelingController {
    @Autowired
    private HandelingService handelingService;

    @PostMapping
    public ResponseEntity<Handeling> createHandeling(@RequestBody HandelingDto handelingDto) {
        Handeling createdHandeling = handelingService.createHandeling(mapToHandeling(handelingDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdHandeling);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Handeling> getHandelingById(@PathVariable Long id) {
        Handeling handeling = handelingService.getHandelingById(id);
        if (handeling != null) {
            return ResponseEntity.ok(handeling);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Handeling> updateHandeling(@PathVariable Long id, @RequestBody HandelingDto handelingDto) {
        Handeling updatedHandeling = handelingService.updateHandeling(id, handelingDto);
        if (updatedHandeling != null) {
            return ResponseEntity.ok(updatedHandeling);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHandeling(@PathVariable Long id) {
        handelingService.deleteHandeling(id);
        return ResponseEntity.noContent().build();
    }

    private Handeling mapToHandeling(HandelingDto handelingDTO) {
        Handeling handeling = new Handeling();
        handeling.setNaam(handelingDTO.getNaam());
        handeling.setPrijs(handelingDTO.getPrijs());
        return handeling;
    }
}