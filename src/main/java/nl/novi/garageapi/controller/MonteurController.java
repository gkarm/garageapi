package nl.novi.garageapi.controller;



import jakarta.validation.Valid;
import nl.novi.garageapi.Security.MyUserDetails;
import nl.novi.garageapi.dto.MonteurDto;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.service.MonteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/monteurs")
public class MonteurController {
    @Autowired
    private final MonteurService service;

    public MonteurController(MonteurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Monteur> getAllMonteurs() {
        return service.getAllMonteurs();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Monteur> getMonteurById(@PathVariable Long id) {
        Monteur monteur = service.getMonteurById(id);
        if (monteur != null) {
            return ResponseEntity.ok(monteur);
        }
        return ResponseEntity.notFound().build();
    }





    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createMonteur(@AuthenticationPrincipal MyUserDetails myUserDetails, @Valid @RequestBody MonteurDto monteurDto, BindingResult br) {

        if (br.hasFieldErrors()) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fe : br.getFieldErrors()) {
                sb.append(fe.getField());
                sb.append(" : ");
                sb.append(fe.getDefaultMessage());
                sb.append("\n");

            }
            return ResponseEntity.badRequest().body(sb.toString());

        }
        else {
            monteurDto = service.createMonteur(myUserDetails,monteurDto);

            URI uri = URI.create(
                    ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/" + monteurDto.id).toUriString());

            return ResponseEntity.created(uri).body(monteurDto);
        }
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Monteur> updateMonteur(@PathVariable Long id, @RequestBody Monteur monteur) {
        Monteur updatedMonteur = service.updateMonteur(id, monteur);
        if (updatedMonteur != null) {
            return ResponseEntity.ok(updatedMonteur);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMonteur(@PathVariable Long id) {
        service.deleteMonteur(id);
        return ResponseEntity.noContent().build();
    }

}