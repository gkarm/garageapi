package nl.novi.garageapi.controller;

import org.junit.jupiter.api.Test;

import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.service.BonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BonControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BonService bonService;

    @Test
    void getAllBons() throws Exception {
        Bon bon1 = new Bon(1L, 100.0, 121.0, 10.0, 20.0, 15.0);
        Bon bon2 = new Bon(2L, 200.0, 242.0, 10.0, 30.0, 25.0);

        given(bonService.getAllBons()).willReturn(List.of(bon1, bon2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L));
    }

    @Test
    void getBonById_whenFound_returnsBon() throws Exception {
        Bon bon = new Bon(1L, 100.0, 121.0, 10.0, 20.0, 15.0);

        given(bonService.getBonById(1L)).willReturn(bon);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bons/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bedrag").value(100.0));
    }

    @Test
    public void getBonById_whenNotFound_returnsNotFound() throws Exception {
        given(bonService.getBonById(1L)).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/bons/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    void createBon() throws Exception {
        BonDto bonDto = new BonDto(1L, 100.0);
        Bon bon = new Bon(1L, 100.0, 121.0, 10.0, 20.0, 15.0);

        given(bonService.createBon(any(BonDto.class))).willReturn(bon);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/bons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"bedrag\": 100.0 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bedrag").value(100.0));
    }

    @Test
    void updateBon_whenFound_returnsUpdatedBon() throws Exception {
        Bon bon = new Bon(1L, 150.0, 181.5, 10.0, 25.0, 25.0);

        given(bonService.updateBon(eq(1L), any(Bon.class))).willReturn(bon);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/bons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"bedrag\": 150.0 }"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bedrag").value(150.0));
    }

    @Test
    void updateBon_whenNotFound_returnsNotFound() throws Exception {
        given(bonService.updateBon(eq(1L), any(Bon.class))).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/bons/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"bedrag\": 150.0 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteBon() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/bons/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(bonService, times(1)).deleteBon(1L);
    }
}