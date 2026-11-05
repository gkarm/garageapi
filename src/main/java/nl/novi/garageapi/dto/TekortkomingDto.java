package nl.novi.garageapi.dto;



public class TekortkomingDto {

    private Long id;
    private String beschrijving;
    private String oplossing;
    private Long autoId;


    public TekortkomingDto() {
    }


    public TekortkomingDto(Long id, String beschrijving, String oplossing, Long autoId) {
        this.id = id;
        this.beschrijving = beschrijving;
        this.oplossing = oplossing;
        this.autoId = autoId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getOplossing() {
        return oplossing;
    }

    public void setOplossing(String oplossing) {
        this.oplossing = oplossing;
    }

    public Long getAutoId() {
        return autoId;
    }

    public void setAutoId(Long autoId) {
        this.autoId = autoId;
    }
}