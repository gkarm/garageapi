package nl.novi.garageapi.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import nl.novi.garageapi.dto.KeuringDto;
import nl.novi.garageapi.model.Keuring;
import nl.novi.garageapi.repository.KeuringRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class KeuringServiceTest {

    @Mock
    KeuringRepository keuringRepository;

    @InjectMocks
    KeuringService keuringService;

    @Test
    public void testGetAllKeuringen() {
        // Arrange
        Keuring keuring1 = new Keuring();
        Keuring keuring2 = new Keuring();
        when(keuringRepository.findAll()).thenReturn(List.of(keuring1, keuring2));

        // Act
        List<KeuringDto> result = keuringService.getAllKeuringen();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(keuringRepository, times(1)).findAll();
    }

    @Test
    public void testGetKeuringById() {
        // Arrange
        Long keuringId = 1L;
        Keuring keuring = new Keuring();
        keuring.setId(keuringId);
        when(keuringRepository.findById(keuringId)).thenReturn(Optional.of(keuring));

        // Act
        Keuring result = keuringService.getKeuringById(keuringId);

        // Assert
        assertNotNull(result);
        assertEquals(keuringId, result.getId());
        verify(keuringRepository, times(1)).findById(keuringId);
    }

    @Test
    public void testAddKeuring() {
        // Arrange
        Keuring newKeuring = new Keuring();
        newKeuring.setId(1L);

        when(keuringRepository.save(any(Keuring.class))).thenReturn(newKeuring);

        // Act
        Keuring result = keuringService.addKeuring(newKeuring);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(keuringRepository, times(1)).save(newKeuring);
    }

    @Test
    public void testUpdateKeuring() {
        // Arrange
        Long keuringId = 1L;
        Keuring existingKeuring = new Keuring();
        existingKeuring.setId(keuringId);

        Keuring updatedKeuring = new Keuring();
        updatedKeuring.setId(keuringId);

        when(keuringRepository.findById(keuringId)).thenReturn(Optional.of(existingKeuring));
        when(keuringRepository.save(any(Keuring.class))).thenReturn(existingKeuring);

        // Act
        Keuring result = keuringService.updateKeuring(keuringId, updatedKeuring);

        // Assert
        assertNotNull(result);
        assertEquals(keuringId, result.getId());
        verify(keuringRepository, times(1)).findById(keuringId);
        verify(keuringRepository, times(1)).save(any(Keuring.class));
    }

    @Test
    public void testDeleteKeuring() throws Exception {
        // Arrange
        Long keuringId = 1L;

        when(keuringRepository.findById(keuringId)).thenReturn(Optional.of(new Keuring()));

        // Act
        keuringService.deleteKeuring(keuringId);

        // Assert
        verify(keuringRepository, times(1)).deleteById(keuringId);
    }

    @Test
    public void testDeleteKeuring_NotFound() {
        // Arrange
        Long keuringId = 1L;

        when(keuringRepository.findById(keuringId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(Exception.class, () -> {
            keuringService.deleteKeuring(keuringId);
        });

        assertTrue(exception.getMessage().contains("could not be found"));
        verify(keuringRepository, never()).deleteById(keuringId);
    }


}