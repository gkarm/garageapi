package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.KlantDto;
import nl.novi.garageapi.model.Klant;
import nl.novi.garageapi.repository.KlantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KlantServiceTest {

    @Mock
    KlantRepository klantRepository;

    @InjectMocks
    KlantService klantService;

    @Test
    void testGetAllKlanten() {
        // Arrange
        Klant klant1 = new Klant();
        klant1.setFirstName("Jan");
        Klant klant2 = new Klant();
        klant2.setFirstName("Piet");

        when(klantRepository.findAll()).thenReturn(Arrays.asList(klant1, klant2));

        // Act
        List<Klant> result = klantService.getAllKlanten();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Jan", result.get(0).getFirstName());
        verify(klantRepository, times(1)).findAll();
    }

    @Test
    void testGetKlantById_Found() {
        // Arrange
        Klant klant = new Klant();
        klant.setId(1L);
        klant.setFirstName("Karel");

        when(klantRepository.findById(1L)).thenReturn(Optional.of(klant));

        // Act
        Klant result = klantService.getKlantById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Karel", result.getFirstName());
        verify(klantRepository, times(1)).findById(1L);
    }

    @Test
    void testGetKlantById_NotFound() {
        // Arrange
        when(klantRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Klant result = klantService.getKlantById(99L);

        // Assert
        assertNull(result);
        verify(klantRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateKlant() {
        // Arrange
        KlantDto klantDto = new KlantDto();
        klantDto.setFirstName("Jan");
        klantDto.setLastName("Jansen");
        klantDto.setDob(LocalDate.of(1990, 1, 1));
        klantDto.setPhone("0612345678");

        Klant klantEntity = new Klant();
        klantEntity.setId(1L);
        klantEntity.setFirstName("Jan");
        klantEntity.setLastName("Jansen");
        klantEntity.setDob(klantDto.getDob());
        klantEntity.setPhone(klantDto.getPhone());

        when(klantRepository.save(any(Klant.class))).thenAnswer(invocation -> {
            Klant saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        // Act
        KlantDto result = klantService.createKlant(klantDto);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Jan", result.getFirstName());
        verify(klantRepository, times(1)).save(any(Klant.class));
    }

    @Test
    void testUpdateKlant_Found() {
        // Arrange
        Klant klant = new Klant();
        klant.setFirstName("Old Name");

        when(klantRepository.existsById(1L)).thenReturn(true);
        when(klantRepository.save(any(Klant.class))).thenReturn(klant);

        // Act
        Klant updated = klantService.updateKlant(1L, klant);

        // Assert
        assertNotNull(updated);
        assertEquals("Old Name", updated.getFirstName());
        verify(klantRepository, times(1)).save(any(Klant.class));
    }

    @Test
    void testUpdateKlant_NotFound() {
        // Arrange
        Klant klant = new Klant();
        klant.setFirstName("Name");

        when(klantRepository.existsById(1L)).thenReturn(false);

        // Act
        Klant result = klantService.updateKlant(1L, klant);

        // Assert
        assertNull(result);
        verify(klantRepository, never()).save(any(Klant.class));
    }

    @Test
    void testDeleteKlant() {
        // Act
        klantService.deleteKlant(1L);

        // Assert
        verify(klantRepository, times(1)).deleteById(1L);
    }
}
