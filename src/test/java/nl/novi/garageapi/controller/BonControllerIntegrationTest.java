package nl.novi.garageapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.repository.BonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class BonControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BonRepository bonRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        bonRepository.deleteAll();
    }

    @Test
    public void testCreateBon() throws Exception {
        BonDto bonDto = new BonDto();
        bonDto.setBedrag(100.0);

        mockMvc.perform(post("/bons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bonDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bedrag").value(100.0))
                .andExpect(jsonPath("$.totaalBedragInclusiefBtw").value(121.0))
                .andExpect(jsonPath("$.keuringBedrag").value(10.0))
                .andExpect(jsonPath("$.handelingenBedrag").value(20.0))
                .andExpect(jsonPath("$.onderdelenBedrag").value(15.0));
    }

    @Test
    public void testGetAllBons() throws Exception {
        Bon bon = new Bon();
        bon.setBedrag(200.0);
        bon.setTotaalBedragInclusiefBtw(242.0);
        bon.setKeuringBedrag(10.0);
        bon.setHandelingenBedrag(20.0);
        bon.setOnderdelenBedrag(15.0);
        bonRepository.save(bon);

        mockMvc.perform(get("/bons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testGetBonById() throws Exception {
        Bon bon = new Bon();
        bon.setBedrag(150.0);
        bon.setTotaalBedragInclusiefBtw(181.5);
        bon.setKeuringBedrag(10.0);
        bon.setHandelingenBedrag(20.0);
        bon.setOnderdelenBedrag(15.0);
        bon = bonRepository.save(bon);

        mockMvc.perform(get("/bons/{id}", bon.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bedrag").value(150.0));
    }

    @Test
    public void testUpdateBon() throws Exception {
        Bon bon = new Bon();
        bon.setBedrag(100.0);
        bon.setTotaalBedragInclusiefBtw(121.0);
        bon.setKeuringBedrag(5.0);
        bon.setHandelingenBedrag(10.0);
        bon.setOnderdelenBedrag(20.0);
        bon = bonRepository.save(bon);

        bon.setKeuringBedrag(15.0);
        bon.setHandelingenBedrag(25.0);
        bon.setOnderdelenBedrag(30.0);

        mockMvc.perform(put("/bons/{id}", bon.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bon)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.keuringBedrag").value(15.0))
                .andExpect(jsonPath("$.handelingenBedrag").value(25.0))
                .andExpect(jsonPath("$.onderdelenBedrag").value(30.0));
    }

    @Test
    public void testDeleteBon() throws Exception {
        Bon bon = new Bon();
        bon.setBedrag(100.0);
        bon.setTotaalBedragInclusiefBtw(121.0);
        bon.setKeuringBedrag(10.0);
        bon.setHandelingenBedrag(20.0);
        bon.setOnderdelenBedrag(15.0);
        bon = bonRepository.save(bon);

        mockMvc.perform(delete("/bons/{id}", bon.getId()))
                .andExpect(status().isNoContent());

        Optional<Bon> deletedBon = bonRepository.findById(bon.getId());
        assertThat(deletedBon).isEmpty();
    }
}
