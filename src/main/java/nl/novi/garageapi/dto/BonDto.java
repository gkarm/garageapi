package nl.novi.garageapi.dto;

public class BonDto {
    public Long id;
    public double bedrag;

    public BonDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBedrag() {
        return bedrag;
    }

    public void setBedrag(double bedrag) {
        this.bedrag = bedrag;
    }

    public BonDto(Long id, double bedrag) {
        this.id = id;
        this.bedrag = bedrag;
    }


}
