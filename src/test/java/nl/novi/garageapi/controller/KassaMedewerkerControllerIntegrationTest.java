package nl.novi.garageapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.garageapi.dto.KassaMedewerkerDto;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.repository.KassaMedewerkerRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class KassaMedewerkerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KassaMedewerkerRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testCreateAndGetKassaMedewerker() throws Exception {
        KassaMedewerkerDto dto = new KassaMedewerkerDto();
        dto.firstName = "Jane";
        dto.lastName = "Doe";
        dto.dob = LocalDate.of(1995, 5, 15);

        mockMvc.perform(post("/kassamedewerkers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testGetAllKassaMedewerkers() throws Exception {
        KassaMedewerker km1 = new KassaMedewerker();
        km1.setFirstName("John");
        km1.setLastName("Smith");
        km1.setDob(LocalDate.of(1990, 1, 1));

        KassaMedewerker km2 = new KassaMedewerker();
        km2.setFirstName("Alice");
        km2.setLastName("Brown");
        km2.setDob(LocalDate.of(1985, 3, 10));

        repository.save(km1);
        repository.save(km2);

        mockMvc.perform(get("/kassamedewerkers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testUpdateKassaMedewerker() throws Exception {
        KassaMedewerker km = new KassaMedewerker();
        km.setFirstName("Old");
        km.setLastName("Name");
        km.setDob(LocalDate.of(1980, 1, 1));
        KassaMedewerker saved = repository.save(km);

        saved.setFirstName("New");
        saved.setLastName("Name");

        mockMvc.perform(put("/kassamedewerkers/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("New"));
    }

    @Test
    void testDeleteKassaMedewerker() throws Exception {
        KassaMedewerker km = new KassaMedewerker();
        km.setFirstName("Delete");
        km.setLastName("Me");
        km.setDob(LocalDate.of(1990, 12, 12));
        KassaMedewerker saved = repository.save(km);

        mockMvc.perform(delete("/kassamedewerkers/" + saved.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/kassamedewerkers/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
}
