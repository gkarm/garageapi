package nl.novi.garageapi.service;

import nl.novi.garageapi.dto.TekortkomingDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.Auto;
import nl.novi.garageapi.model.Tekortkoming;
import nl.novi.garageapi.repository.AutoRepository;
import nl.novi.garageapi.repository.TekortkomingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TekortkomingService {

    private final TekortkomingRepository tekortkomingRepository;
    private final AutoRepository autoRepository;


    public TekortkomingService(
            TekortkomingRepository tekortkomingRepository,
            AutoRepository autoRepository) {
        this.tekortkomingRepository = tekortkomingRepository;
        this.autoRepository = autoRepository;
    }
    public List<TekortkomingDto> getAllTekortkomingen() {
        return tekortkomingRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    public TekortkomingDto getTekortkomingById(Long id) {
        Tekortkoming tekortkoming = tekortkomingRepository.findById(id)
                .orElse(null);

        if (tekortkoming == null) {
            return null;
        }

        return convertToDto(tekortkoming);
    }
    public TekortkomingDto addTekortkomingToAuto(
            TekortkomingDto tekortkomingDto) {

        Auto auto = autoRepository.findById(tekortkomingDto.getAutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Auto not found with id "
                                        + tekortkomingDto.getAutoId()));

        Tekortkoming tekortkoming = new Tekortkoming();
        tekortkoming.setBeschrijving(tekortkomingDto.getBeschrijving());
        tekortkoming.setOplossing(tekortkomingDto.getOplossing());
        tekortkoming.setAuto(auto);

        Tekortkoming savedTekortkoming =
                tekortkomingRepository.save(tekortkoming);

        return convertToDto(savedTekortkoming);
    }
    public TekortkomingDto updateTekortkoming(
            Long id, TekortkomingDto tekortkomingDto) {

        Tekortkoming tekortkoming = tekortkomingRepository.findById(id)
                .orElse(null);

        if (tekortkoming == null) {
            return null;
        }

        tekortkoming.setBeschrijving(tekortkomingDto.getBeschrijving());
        tekortkoming.setOplossing(tekortkomingDto.getOplossing());


        if (tekortkomingDto.getAutoId() != null) {
            Auto auto = autoRepository.findById(tekortkomingDto.getAutoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Auto not found with id "
                                            + tekortkomingDto.getAutoId()));

            tekortkoming.setAuto(auto);
        }

        Tekortkoming updatedTekortkoming =
                tekortkomingRepository.save(tekortkoming);

        return convertToDto(updatedTekortkoming);
    }
    public boolean deleteTekortkoming(Long id) {
        if (!tekortkomingRepository.existsById(id)) {
            return false;
        }

        tekortkomingRepository.deleteById(id);
        return true;
    }
    private TekortkomingDto convertToDto(Tekortkoming tekortkoming) {

        Long autoId = null;

        if (tekortkoming.getAuto() != null) {
            autoId = tekortkoming.getAuto().getId();
        }

        return new TekortkomingDto(
                tekortkoming.getId(),
                tekortkoming.getBeschrijving(),
                tekortkoming.getOplossing(),
                autoId
        );
    }
}
