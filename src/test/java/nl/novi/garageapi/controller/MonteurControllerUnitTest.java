package nl.novi.garageapi.controller;

import org.junit.jupiter.api.Test;

import nl.novi.garageapi.Security.JwtService;
import nl.novi.garageapi.dto.MonteurDto;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.service.MonteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MonteurController.class)
@AutoConfigureMockMvc(addFilters = false)
class MonteurControllerUnitTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    MonteurService monteurService;

    @Test
    void testGetAllMonteurs() throws Exception {
        // Create some mock data
        Monteur monteur1 = new Monteur();
        monteur1.setFirstName("Koffi");
        monteur1.setLastName("Gombo");
        monteur1.setDob(LocalDate.of(1990, 1, 1));

        Monteur monteur2 = new Monteur();
        monteur2.setFirstName("Anon");
        monteur2.setLastName("Gogo");
        monteur2.setDob(LocalDate.of(1995, 5, 5));

        List<Monteur> monteurs = Arrays.asList(monteur1, monteur2);

        when(monteurService.getAllMonteurs()).thenReturn(monteurs);

        mockMvc.perform(get("/monteurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Koffi"))
                .andExpect(jsonPath("$[1].firstName").value("Anon"));
    }

    @Test
    void testGetMonteurById_Found() throws Exception {
        Monteur monteur = new Monteur();
        monteur.setFirstName("Koffi");
        monteur.setLastName("Gombo");
        monteur.setDob(LocalDate.of(1990, 1, 1));

        when(monteurService.getMonteurById(1L)).thenReturn(monteur);

        mockMvc.perform(get("/monteurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Koffi"))
                .andExpect(jsonPath("$.lastName").value("Gombo"));
    }

    @Test
    void testGetMonteurById_NotFound() throws Exception {
        when(monteurService.getMonteurById(1L)).thenReturn(null);

        mockMvc.perform(get("/monteurs/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateMonteur_ValidData() throws Exception {
        MonteurDto monteurDto = new MonteurDto();
        monteurDto.firstName = "Koffi";
        monteurDto.lastName = "Gombo";
        monteurDto.dob = LocalDate.of(1990, 1, 1);

        when(monteurService.createMonteur(any(), any(MonteurDto.class))).thenReturn(monteurDto);

        mockMvc.perform(
                        post("/monteurs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"Koffi\", \"lastName\": \"Gombo\", \"dob\": \"1990-01-01\"}")
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateMonteur_ExistingId() throws Exception {
        Monteur monteur = new Monteur();
        monteur.setFirstName("Koffi");
        monteur.setLastName("Gombo");
        monteur.setDob(LocalDate.of(1990, 1, 1));

        when(monteurService.updateMonteur(eq(1L), any(Monteur.class))).thenReturn(monteur);

        mockMvc.perform(
                        put("/monteurs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"Koffi\", \"lastName\": \"Gombo\", \"dob\": \"1990-01-01\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Koffi"));
    }

    @Test
    void testUpdateMonteur_NonExistingId() throws Exception {
        when(monteurService.updateMonteur(eq(1L), any(Monteur.class))).thenReturn(null);

        mockMvc.perform(
                        put("/monteurs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"firstName\": \"Koffi\", \"lastName\": \"Gombo\", \"dob\": \"1990-01-01\"}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteMonteur_ExistingId() throws Exception {
        mockMvc.perform(delete("/monteurs/1"))
                .andExpect(status().isNoContent());
    }



}