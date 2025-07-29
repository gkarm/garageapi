package nl.novi.garageapi.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import nl.novi.garageapi.dto.BonDto;
import nl.novi.garageapi.model.Bon;
import nl.novi.garageapi.repository.BonRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BonServiceTest {
    @Mock
    BonRepository bonRepository;
    @InjectMocks
    BonService bonService;

    @Test
    public void testGetAllBons() {
        // Arrange
        Bon bon1 = new Bon();
        Bon bon2 = new Bon();
        when(bonRepository.findAll()).thenReturn(Arrays.asList(bon1, bon2));

        // Act
        List<Bon> result = bonService.getAllBons();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bonRepository, times(1)).findAll();
    }

    @Test
    public void testGetBonById() {
        // Arrange
        Long bonId = 1L;
        Bon bon = new Bon();
        bon.setId(bonId);
        when(bonRepository.findById(bonId)).thenReturn(Optional.of(bon));

        // Act
        Bon result = bonService.getBonById(bonId);

        // Assert
        assertNotNull(result);
        assertEquals(bonId, result.getId());
        verify(bonRepository, times(1)).findById(bonId);
    }

    @Test
    public void testCreateBon() {
        // Arrange
        BonDto bonDto = new BonDto();
        bonDto.setBedrag(100.0);

        Bon expectedBon = new Bon();
        expectedBon.setBedrag(100.0);
        expectedBon.setTotaalBedragInclusiefBtw(121.0);
        expectedBon.setKeuringBedrag(10.0);
        expectedBon.setHandelingenBedrag(20.0);
        expectedBon.setOnderdelenBedrag(15.0);

        when(bonRepository.save(any(Bon.class))).thenReturn(expectedBon);

        // Act
        Bon result = bonService.createBon(bonDto);

        // Assert
        assertNotNull(result);
        assertEquals(expectedBon.getBedrag(), result.getBedrag());
        assertEquals(expectedBon.getTotaalBedragInclusiefBtw(), result.getTotaalBedragInclusiefBtw());
        verify(bonRepository, times(1)).save(any(Bon.class));
    }

    @Test
    public void testUpdateBon() {
        // Arrange
        Long bonId = 1L;
        Bon existingBon = new Bon();
        existingBon.setId(bonId);
        existingBon.setKeuringBedrag(10.0);
        existingBon.setHandelingenBedrag(20.0);
        existingBon.setOnderdelenBedrag(15.0);

        Bon updatedBon = new Bon();
        updatedBon.setKeuringBedrag(12.0);
        updatedBon.setHandelingenBedrag(22.0);
        updatedBon.setOnderdelenBedrag(17.0);

        when(bonRepository.findById(bonId)).thenReturn(Optional.of(existingBon));
        when(bonRepository.save(existingBon)).thenReturn(existingBon);

        // Act
        Bon result = bonService.updateBon(bonId, updatedBon);

        // Assert
        assertNotNull(result);
        assertEquals(12.0, result.getKeuringBedrag());
        assertEquals(22.0, result.getHandelingenBedrag());
        assertEquals(17.0, result.getOnderdelenBedrag());
        verify(bonRepository, times(1)).findById(bonId);
        verify(bonRepository, times(1)).save(existingBon);
    }

    @Test
    public void testDeleteBon() {
        // Arrange
        Long bonId = 1L;

        // Act
        bonService.deleteBon(bonId);

        // Assert
        verify(bonRepository, times(1)).deleteById(bonId);
    }


}