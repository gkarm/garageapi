package nl.novi.garageapi.controller;

import org.junit.jupiter.api.Test;
import nl.novi.garageapi.Security.JwtService;
import nl.novi.garageapi.dto.KassaMedewerkerDto;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.service.KassaMedewerkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KassaMedewerkerController.class)
@AutoConfigureMockMvc(addFilters = false)

class KassaMedewerkerControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    KassaMedewerkerService service;

    @Test
    void testGetAllKassaMedewerkers() throws Exception {
        KassaMedewerker kassaMedewerker = new KassaMedewerker();
        kassaMedewerker.setFirstName("Koffi");
        kassaMedewerker.setLastName("Gombo");
        kassaMedewerker.setDob(LocalDate.of(1990, 1, 1));

        when(service.getAllKassamedewerkers()).thenReturn(Collections.singletonList(kassaMedewerker));

        mockMvc.perform(get("/kassamedewerkers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Koffi"))
                .andExpect(jsonPath("$[0].lastName").value("Gombo"));
    }

    @Test
    void testGetKassaMedewerkerById() throws Exception {
        KassaMedewerker kassaMedewerker = new KassaMedewerker();
        kassaMedewerker.setFirstName("Koffi");
        kassaMedewerker.setLastName("Gombo");
        kassaMedewerker.setDob(LocalDate.of(1990, 1, 1));

        when(service.getKassamedewerkerById(anyLong())).thenReturn(kassaMedewerker);

        mockMvc.perform(get("/kassamedewerkers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Koffi"))
                .andExpect(jsonPath("$.lastName").value("Gombo"));
    }

    @Test
    void testCreateKassaMedewerker() throws Exception {
        KassaMedewerkerDto dto = new KassaMedewerkerDto();
        dto.firstName = "Koffi";
        dto.lastName = "Gombo";
        dto.dob = LocalDate.of(1990, 1, 1);

        when(service.createKassaMedewerker(any(KassaMedewerkerDto.class))).thenReturn(dto);

        String jsonRequest = "{ \"firstName\": \"Koffi\", \"lastName\": \"Gombo\", \"dob\": \"1990-01-01\" }";

        mockMvc.perform(post("/kassamedewerkers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Koffi"))
                .andExpect(jsonPath("$.lastName").value("Gombo"));
    }

    @Test
    void testDeleteKassaMedewerker() throws Exception {
        mockMvc.perform(delete("/kassamedewerkers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateKassaMedewerker() throws Exception {
        KassaMedewerker kassaMedewerker = new KassaMedewerker();
        kassaMedewerker.setFirstName("Koffi");
        kassaMedewerker.setLastName("Gombo");
        kassaMedewerker.setDob(LocalDate.of(1990, 1, 1));

        when(service.updateKassamedewerker(anyLong(), any(KassaMedewerker.class))).thenReturn(kassaMedewerker);

        String jsonRequest = "{ \"firstName\": \"Koffi\", \"lastName\": \"Gombo\", \"dob\": \"1990-01-01\" }";

        mockMvc.perform(put("/kassamedewerkers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Koffi"))
                .andExpect(jsonPath("$.lastName").value("Gombo"));
    }
}