package nl.novi.garageapi.controller;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.garageapi.Security.JwtService;
import nl.novi.garageapi.dto.KeuringDto;
import nl.novi.garageapi.model.Auto;
import nl.novi.garageapi.model.Keuring;
import nl.novi.garageapi.model.Monteur;
import nl.novi.garageapi.service.KeuringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KeuringController.class)
@AutoConfigureMockMvc(addFilters = false)
class KeuringControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private KeuringService keuringService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllKeuringen() throws Exception {
        KeuringDto dto1 = new KeuringDto();
        dto1.setId(1L);
        dto1.setKeuringsResultaat("Goedgekeurd");
        dto1.setStatus("Voltooid");

        KeuringDto dto2 = new KeuringDto();
        dto2.setId(2L);
        dto2.setKeuringsResultaat("Afgekeurd");
        dto2.setStatus("In behandeling");

        List<KeuringDto> dtos = Arrays.asList(dto1, dto2);

        when(keuringService.getAllKeuringen()).thenReturn(dtos);

        mockMvc.perform(get("/keuringen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].keuringsResultaat").value("Goedgekeurd"))
                .andExpect(jsonPath("$[1].status").value("In behandeling"));
    }

    @Test
    void testGetKeuringById_Found() throws Exception {
        Keuring keuring = new Keuring();
        keuring.setId(1L);
        keuring.setKeuringsResultaat("Goedgekeurd");
        keuring.setStatus("Voltooid");

        KeuringDto dto = new KeuringDto();
        dto.setId(1L);
        dto.setKeuringsResultaat("Goedgekeurd");
        dto.setStatus("Voltooid");

        when(keuringService.getKeuringById(1L)).thenReturn(keuring);
        when(keuringService.keuringDtoFromKeuring(keuring)).thenReturn(dto);

        mockMvc.perform(get("/keuringen/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keuringsResultaat").value("Goedgekeurd"))
                .andExpect(jsonPath("$.status").value("Voltooid"));
    }

    @Test
    void testGetKeuringById_NotFound() throws Exception {
        when(keuringService.getKeuringById(1L)).thenReturn(null);

        mockMvc.perform(get("/keuringen/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddKeuring_ValidData() throws Exception {
        Keuring keuring = new Keuring();
        keuring.setId(1L);
        keuring.setKeuringsResultaat("Goedgekeurd");
        keuring.setStatus("Voltooid");
        keuring.setDatum(new Date());
        keuring.setOpmerking("Alles in orde");
        keuring.setMonteur(new Monteur());
        keuring.setAuto(new Auto());

        when(keuringService.addKeuring(any(Keuring.class))).thenReturn(keuring);

        mockMvc.perform(post("/keuringen")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(keuring)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.keuringsResultaat").value("Goedgekeurd"))
                .andExpect(jsonPath("$.status").value("Voltooid"));
    }

    @Test
    void testDeleteKeuring_ExistingId() throws Exception {
        mockMvc.perform(delete("/keuringen/1"))
                .andExpect(status().isNoContent());
    }
}
