package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.KassaMedewerkerDto;
import nl.novi.garageapi.model.KassaMedewerker;
import nl.novi.garageapi.repository.KassaMedewerkerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KassaMedewerkerServiceTest {

    @Mock
    private KassaMedewerkerRepository kassaMedewerkerRepository;

    @InjectMocks
    private KassaMedewerkerService kassaMedewerkerService;

    @Test
    void testGetAllKassamedewerkers() {
        // Arrange
        KassaMedewerker km1 = new KassaMedewerker();
        km1.setId(1L);
        KassaMedewerker km2 = new KassaMedewerker();
        km2.setId(2L);

        when(kassaMedewerkerRepository.findAll()).thenReturn(List.of(km1, km2));

        // Act
        List<KassaMedewerker> result = kassaMedewerkerService.getAllKassamedewerkers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(kassaMedewerkerRepository, times(1)).findAll();
    }

    @Test
    void testGetKassamedewerkerById_Found() {
        // Arrange
        Long id = 1L;
        KassaMedewerker km = new KassaMedewerker();
        km.setId(id);
        when(kassaMedewerkerRepository.findById(id)).thenReturn(Optional.of(km));

        // Act
        KassaMedewerker result = kassaMedewerkerService.getKassamedewerkerById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(kassaMedewerkerRepository, times(1)).findById(id);
    }

    @Test
    void testGetKassamedewerkerById_NotFound() {
        // Arrange
        Long id = 1L;
        when(kassaMedewerkerRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        KassaMedewerker result = kassaMedewerkerService.getKassamedewerkerById(id);

        // Assert
        assertNull(result);
        verify(kassaMedewerkerRepository, times(1)).findById(id);
    }


    @Test
    void testCreateKassaMedewerker() {
        // Arrange
        KassaMedewerkerDto dto = new KassaMedewerkerDto();
        dto.firstName = "Jan";
        dto.lastName = "Jansen";
        dto.dob = LocalDate.of(1990, 5, 15);

        when(kassaMedewerkerRepository.save(any(KassaMedewerker.class)))
                .thenAnswer(invocation -> {
                    KassaMedewerker km = invocation.getArgument(0);
                    km.setId(1L); // Simulate DB-generated ID
                    return km;
                });

        // Act
        KassaMedewerkerDto result = kassaMedewerkerService.createKassaMedewerker(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.id);
        verify(kassaMedewerkerRepository, times(1)).save(any(KassaMedewerker.class));
    }
    @Test
    void testUpdateKassamedewerker_Found() {
        // Arrange
        Long id = 1L;
        KassaMedewerker km = new KassaMedewerker();
        km.setId(id);

        when(kassaMedewerkerRepository.existsById(id)).thenReturn(true);
        when(kassaMedewerkerRepository.save(any(KassaMedewerker.class))).thenReturn(km);

        // Act
        KassaMedewerker result = kassaMedewerkerService.updateKassamedewerker(id, km);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(kassaMedewerkerRepository, times(1)).existsById(id);
        verify(kassaMedewerkerRepository, times(1)).save(km);
    }

    @Test
    void testUpdateKassamedewerker_NotFound() {
        // Arrange
        Long id = 1L;
        KassaMedewerker km = new KassaMedewerker();
        when(kassaMedewerkerRepository.existsById(id)).thenReturn(false);

        // Act
        KassaMedewerker result = kassaMedewerkerService.updateKassamedewerker(id, km);

        // Assert
        assertNull(result);
        verify(kassaMedewerkerRepository, times(1)).existsById(id);
        verify(kassaMedewerkerRepository, never()).save(any(KassaMedewerker.class));
    }

    @Test
    void testDeleteKassamedewerker() {
        // Arrange
        Long id = 1L;

        // Act
        kassaMedewerkerService.deleteKassamedewerker(id);

        // Assert
        verify(kassaMedewerkerRepository, times(1)).deleteById(id);
    }
}
