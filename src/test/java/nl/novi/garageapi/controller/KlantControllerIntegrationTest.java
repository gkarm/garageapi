package nl.novi.garageapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.garageapi.dto.KlantDto;
import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.repository.AutoRepository;
import nl.novi.garageapi.repository.KlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class KlantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private KlantRepository klantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        autoRepository.deleteAll();
        klantRepository.deleteAll();

        Klant klant = new Klant();
        klant.setFirstName("John");
        klant.setLastName("Doe");
        klant.setDob(LocalDate.of(1980, 1, 1));
        klant.setPhone("0612345678");
        klantRepository.save(klant);
    }

    @Test
    public void testGetAllKlanten() throws Exception {
        mockMvc.perform(get("/klanten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void testGetKlantById() throws Exception {
        Klant klant = klantRepository.findAll().get(0);

        mockMvc.perform(get("/klanten/" + klant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testCreateKlant() throws Exception {
        KlantDto klantDto = new KlantDto();
        klantDto.setFirstName("Jane");
        klantDto.setLastName("Smith");
        klantDto.setDob(LocalDate.of(1990, 5, 15));
        klantDto.setPhone("0687654321");

        mockMvc.perform(post("/klanten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(klantDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("Jane")));
    }

    @Test
    public void testUpdateKlant() throws Exception {
        Klant klant = klantRepository.findAll().get(0);
        klant.setFirstName("Updated");

        mockMvc.perform(put("/klanten/" + klant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(klant)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Updated")));
    }

    @Test
    public void testDeleteKlant() throws Exception {
        Klant klant = klantRepository.findAll().get(0);

        mockMvc.perform(delete("/klanten/" + klant.getId()))
                .andExpect(status().isNoContent());
    }
}
