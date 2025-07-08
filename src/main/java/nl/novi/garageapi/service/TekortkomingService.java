package nl.novi.garageapi.service;




import nl.novi.garageapi.dto.TekortkomingDto;
import nl.novi.garageapi.exception.ResourceNotFoundException;
import nl.novi.garageapi.model.Auto;
import nl.novi.garageapi.model.Tekortkoming;
import nl.novi.garageapi.repository.AutoRepository;
import nl.novi.garageapi.repository.TekortkomingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TekortkomingService {
    @Autowired
    private TekortkomingRepository tekortkomingRepository;

    @Autowired
    private AutoRepository autoRepository;




    public Tekortkoming createTekortkoming(Tekortkoming tekortkoming) {
        return tekortkomingRepository.save(tekortkoming);
    }

    public Tekortkoming getTekortkomingById(Long id) {
        return tekortkomingRepository.findById(id).orElse(null);
    }

    public Tekortkoming updateTekortkoming(Long id, TekortkomingDto tekortkomingDto) {
        Tekortkoming tekortkoming = tekortkomingRepository.findById(id).orElse(null);
        if (tekortkoming != null) {
            tekortkoming.setBeschrijving(tekortkomingDto.getBeschrijving());
            tekortkoming.setOplossing(tekortkomingDto.getOplossing());
            return tekortkomingRepository.save(tekortkoming);
        }
        return null;
    }

    public void deleteTekortkoming(Long id) {
        tekortkomingRepository.deleteById(id);
    }



    public Tekortkoming addTekortkomingToAuto( TekortkomingDto tekortkomingdto) {
        Optional<Auto> autoOpt = autoRepository.findById(tekortkomingdto.getAutoId());
        if (autoOpt.isPresent()) {
            Auto auto = autoOpt.get();
            Tekortkoming tekortkoming = new Tekortkoming();
            tekortkoming.setBeschrijving(tekortkomingdto.getBeschrijving());
            tekortkoming.setOplossing(tekortkomingdto.getOplossing());
            tekortkoming.setAuto(auto);
            Tekortkoming savedTekortkoming = tekortkomingRepository.save(tekortkoming);
            return savedTekortkoming;
        } else {
            throw new ResourceNotFoundException("Auto not found with id " + tekortkomingdto.getAutoId());
        }
    }
}