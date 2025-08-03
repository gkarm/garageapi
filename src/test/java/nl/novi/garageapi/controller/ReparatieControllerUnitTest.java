package nl.novi.garageapi.controller;

import nl.novi.garageapi.Security.JwtService;
import nl.novi.garageapi.model.Reparatie;
import nl.novi.garageapi.service.ReparatieService;
import org.junit.jupiter.api.Test;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReparatieController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReparatieControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReparatieService reparatieService;

    @MockBean
    JwtService jwtService;

    @Test
    void testGetAllReparaties() throws Exception {
        Reparatie rep1 = new Reparatie();
        rep1.setId(1L);
        rep1.setBeschrijving("Reparatie A");
        rep1.setTotaalBedrag(100.0);

        Reparatie rep2 = new Reparatie();
        rep2.setId(2L);
        rep2.setBeschrijving("Reparatie B");
        rep2.setTotaalBedrag(200.0);

        List<Reparatie> reparaties = Arrays.asList(rep1, rep2);
        when(reparatieService.getAllReparaties()).thenReturn(reparaties);

        mockMvc.perform(get("/reparaties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].beschrijving").value("Reparatie A"))
                .andExpect(jsonPath("$[1].beschrijving").value("Reparatie B"));
    }

    @Test
    void testGetReparatieById_Found() throws Exception {
        Reparatie rep = new Reparatie();
        rep.setId(1L);
        rep.setBeschrijving("Reparatie A");

        when(reparatieService.getReparatieById(1L)).thenReturn(rep);

        mockMvc.perform(get("/reparaties/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beschrijving").value("Reparatie A"));
    }

    @Test
    void testGetReparatieById_NotFound() throws Exception {
        when(reparatieService.getReparatieById(1L)).thenReturn(null);

        mockMvc.perform(get("/reparaties/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddReparatie() throws Exception {
        Reparatie input = new Reparatie();
        input.setBeschrijving("Nieuwe reparatie");
        input.setTotaalBedrag(123.45);
        input.setDatum(new Date());

        Reparatie saved = new Reparatie();
        saved.setId(1L);
        saved.setBeschrijving("Nieuwe reparatie");
        saved.setTotaalBedrag(123.45);

        when(reparatieService.addReparatie(any(Reparatie.class))).thenReturn(saved);

        mockMvc.perform(post("/reparaties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"beschrijving\": \"Nieuwe reparatie\", \"totaalBedrag\": 123.45}")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.beschrijving").value("Nieuwe reparatie"))
                .andExpect(jsonPath("$.totaalBedrag").value(123.45));
    }

    @Test
    void testUpdateReparatie_ExistingId() throws Exception {
        Reparatie updated = new Reparatie();
        updated.setId(1L);
        updated.setBeschrijving("Bijgewerkte reparatie");
        updated.setTotaalBedrag(300.0);

        when(reparatieService.updateReparatie(eq(1L), any(Reparatie.class))).thenReturn(updated);

        mockMvc.perform(put("/reparaties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"beschrijving\": \"Bijgewerkte reparatie\", \"totaalBedrag\": 300.0}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beschrijving").value("Bijgewerkte reparatie"));
    }

    @Test
    void testUpdateReparatie_NonExistingId() throws Exception {
        when(reparatieService.updateReparatie(eq(1L), any(Reparatie.class))).thenReturn(null);

        mockMvc.perform(put("/reparaties/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"beschrijving\": \"Bijgewerkte reparatie\", \"totaalBedrag\": 300.0}")
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteReparatie() throws Exception {
        doNothing().when(reparatieService).deleteReparatie(1L);

        mockMvc.perform(delete("/reparaties/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSetReparatieNietUitvoeren() throws Exception {
        doNothing().when(reparatieService).setReparatieNietUitvoeren(1L);

        mockMvc.perform(put("/reparaties/1/niet-uitvoeren"))
                .andExpect(status().isOk());
    }

    @Test
    void testKoppelBonAanReparatie() throws Exception {
        doNothing().when(reparatieService).koppelBonAanReparatie(1L, 42L);

        mockMvc.perform(put("/reparaties/1/koppel-bon")
                        .param("bonId", "42"))
                .andExpect(status().isOk());

        verify(reparatieService).koppelBonAanReparatie(1L, 42L);
    }

    @Test
    void testVoegOnderdeelToeAanReparatie() throws Exception {
        doNothing().when(reparatieService).voegGebruiktOnderdeelToeAanReparatie(1L, 5L);

        mockMvc.perform(put("/reparaties/1/toevoegen-onderdeel")
                        .param("onderdeelId", "5"))
                .andExpect(status().isOk());

        verify(reparatieService).voegGebruiktOnderdeelToeAanReparatie(1L, 5L);
    }

    @Test
    void testVoegHandelingToeAanReparatie() throws Exception {
        doNothing().when(reparatieService).voegGebruikteHandelingToeAanReparatie(1L, 7L);

        mockMvc.perform(put("/reparaties/1/toevoegen-handeling")
                        .param("handelingId", "7"))
                .andExpect(status().isOk());

        verify(reparatieService).voegGebruikteHandelingToeAanReparatie(1L, 7L);
    }

    @Test
    void testVoegOnderdeelEnHandelingToeAanReparatie() throws Exception {
        doNothing().when(reparatieService)
                .voegOnderdeelEnHandelingToeAanReparatie(1L, 3L, 8L);

        mockMvc.perform(put("/reparaties/1/toevoegen")
                        .param("onderdeelId", "3")
                        .param("handelingId", "8"))
                .andExpect(status().isOk());

        verify(reparatieService).voegOnderdeelEnHandelingToeAanReparatie(1L, 3L, 8L);
    }
}
