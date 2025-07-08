package nl.novi.garageapi.dto;



import lombok.Getter;
import lombok.Setter;
import nl.novi.garageapi.model.Tekortkoming;
import nl.novi.garageapi.repository.AutoRepository;

public class TekortkomingDto extends Tekortkoming {


    private final AutoRepository autoRepository;
    @Getter
    private Long id;
    @Setter
    @Getter
    private Long autoId;


    @Setter
    @Getter
    private String beschrijving;
    @Setter
    @Getter
    private String oplossing;

    public TekortkomingDto(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }


    public TekortkomingDto(AutoRepository autoRepository, String beschrijving, String oplossing, Long autoId) {
        this.autoRepository = autoRepository;
        this.beschrijving = beschrijving;
        this.oplossing = oplossing;
        this.autoId = autoId;

    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AutoRepository getAutoRepository() {
        return autoRepository;
    }

    public Long getId() {
        return id;
    }

    public Long getAutoId() {
        return autoId;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getOplossing() {
        return oplossing;
    }
}