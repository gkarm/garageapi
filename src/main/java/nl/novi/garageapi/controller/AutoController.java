package nl.novi.garageapi.controller;

import jakarta.validation.Valid;
import nl.novi.garageapi.dto.AutoDto;
import nl.novi.garageapi.model.Auto;
import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.repository.AutoRepository;
import nl.novi.garageapi.repository.KlantRepository;
import nl.novi.garageapi.repository.MonteurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/autos")
public class AutoController {

    private final AutoRepository autoRepos;
    private final MonteurRepository monteurRepos;
    private final KlantRepository klantRepos;

    public AutoController(AutoRepository autoRepository, MonteurRepository monteurRepository, KlantRepository klantRepository) {
        this.autoRepos = autoRepository;
        this.monteurRepos = monteurRepository;
        this.klantRepos = klantRepository;
    }

    @PostMapping
    public ResponseEntity<AutoDto> createAuto(@Valid @RequestBody AutoDto autoDto, BindingResult br) {
        Auto auto = new Auto();
        auto.setModel(autoDto.model);
        auto.setMerk(autoDto.merk);
        auto.setKenteken(autoDto.kenteken);
        auto.setBouwjaar(autoDto.bouwjaar);

        for (Long id : autoDto.monteurIds) {
            Optional<Monteur> om = monteurRepos.findById(id);
            om.ifPresent(auto.getMonteurs()::add);
        }

        Optional<Klant> klantOptional = klantRepos.findById(autoDto.klantId);
        if (klantOptional.isPresent()) {
            auto.setKlant(klantOptional.get());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        autoRepos.save(auto);
        autoDto.id = auto.getId();
        return new ResponseEntity<>(autoDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutoDto> getAutoById(@PathVariable Long id) {
        Optional<Auto> autoOptional = autoRepos.findById(id);
        if (autoOptional.isPresent()) {
            Auto auto = autoOptional.get();
            AutoDto autoDto = new AutoDto();
            autoDto.id = auto.getId();
            autoDto.merk = auto.getMerk();
            autoDto.model = auto.getModel();
            autoDto.kenteken = auto.getKenteken();
            autoDto.bouwjaar = auto.getBouwjaar();
            autoDto.klantId = auto.getKlant().getId();

            return new ResponseEntity<>(autoDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutoDto> updateAuto(@PathVariable Long id, @Valid @RequestBody AutoDto autoDto, BindingResult br) {
        Optional<Auto> autoOptional = autoRepos.findById(id);
        if (autoOptional.isPresent()) {
            Auto auto = autoOptional.get();
            auto.setModel(autoDto.model);
            auto.setMerk(autoDto.merk);
            auto.setKenteken(autoDto.kenteken);
            auto.setBouwjaar(autoDto.bouwjaar);

            for (Long monteurId : autoDto.monteurIds) {
                Optional<Monteur> om = monteurRepos.findById(monteurId);
                om.ifPresent(auto.getMonteurs()::add);
            }

            Optional<Klant> klantOptional = klantRepos.findById(autoDto.klantId);
            if (klantOptional.isPresent()) {
                auto.setKlant(klantOptional.get());
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            autoRepos.save(auto);
            autoDto.id = auto.getId();
            return new ResponseEntity<>(autoDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuto(@PathVariable Long id) {
        Optional<Auto> autoOptional = autoRepos.findById(id);
        if (autoOptional.isPresent()) {
            autoRepos.delete(autoOptional.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}