package nl.novi.garageapi.service;


import nl.novi.garageapi.dto.AutoDto;
import nl.novi.garageapi.model.*;
import nl.novi.garageapi.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoService {

    @Autowired
    private final AutoRepository repos;
    private final MonteurRepository monteurRepos;
    private final KlantRepository klantRepository;

    public AutoService(AutoRepository repos, MonteurRepository monteurRepos, KlantRepository klantRepository) {
        this.repos = repos;
        this.monteurRepos = monteurRepos;
        this.klantRepository = klantRepository;
    }

    public List<Auto> getAllAutos() {
        return repos.findAll();
    }

    public Auto getAutoById(Long id) {
        return repos.findById(id).orElse(null);
    }

    public AutoDto createAuto(AutoDto autoDto) {
        Auto auto = new Auto();
        auto.setBouwjaar(autoDto.bouwjaar);
        auto.setMerk(autoDto.merk);
        auto.setKenteken(autoDto.kenteken);
        auto.setModel(autoDto.model);

        for (Long id : autoDto.monteurIds) {
            Optional<Monteur> om = monteurRepos.findById(id);
            om.ifPresent(auto.getMonteurs()::add);
        }

        Optional<Klant> klantOptional = klantRepository.findById(autoDto.klantId);
        if (klantOptional.isPresent()) {
            auto.setKlant(klantOptional.get());
        } else {
            throw new IllegalArgumentException("Invalid klant ID");
        }

        repos.save(auto);
        autoDto.id = auto.getId();
        return autoDto;
    }

    public AutoDto updateAuto(Long id, AutoDto autoDto) {
        Optional<Auto> autoOptional = repos.findById(id);
        if (autoOptional.isPresent()) {
            Auto auto = autoOptional.get();
            auto.setModel(autoDto.model);
            auto.setMerk(autoDto.merk);
            auto.setKenteken(autoDto.kenteken);
            auto.setBouwjaar(autoDto.bouwjaar);

            auto.getMonteurs().clear();
            for (Long monteurId : autoDto.monteurIds) {
                Optional<Monteur> om = monteurRepos.findById(monteurId);
                om.ifPresent(auto.getMonteurs()::add);
            }

            Optional<Klant> klantOptional = klantRepository.findById(autoDto.klantId);
            if (klantOptional.isPresent()) {
                auto.setKlant(klantOptional.get());
            } else {
                throw new IllegalArgumentException("Invalid klant ID");
            }

            repos.save(auto);
            autoDto.id = auto.getId();
            return autoDto;
        }
        throw new IllegalArgumentException("Auto not found");
    }

    public void deleteAuto(Long id) {
        repos.deleteById(id);
    }

    public void addTekortkomingToAuto(Long autoId, Tekortkoming tekortkoming) {
        Optional<Auto> autoOptional = repos.findById(autoId);
        if (autoOptional.isPresent()) {
            Auto auto = autoOptional.get();
            tekortkoming.setAuto(auto);
            auto.getTekortkomingen().add(tekortkoming);
            repos.save(auto);
        } else {
            throw new IllegalArgumentException("Auto not found");
        }
    }
}