//package nl.novi.garageapi.dto;
//
//public class BonDto {
//    public Long id;
//    public double bedrag;
//
//    public BonDto() {
//
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public double getBedrag() {
//        return bedrag;
//    }
//
//    public void setBedrag(double bedrag) {
//        this.bedrag = bedrag;
//    }
//
//    public BonDto(Long id, double bedrag) {
//        this.id = id;
//        this.bedrag = bedrag;
//    }
//
//
//}



//package nl.novi.garageapi.dto;
//
//public class BonDto {
//    private Long id;
//    private double bedrag;
//    private double totaalBedragInclusiefBtw;
//    private double keuringBedrag;
//    private double handelingenBedrag;
//    private double onderdelenBedrag;
//
//    public BonDto() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public double getBedrag() {
//        return bedrag;
//    }
//
//    public void setBedrag(double bedrag) {
//        this.bedrag = bedrag;
//    }
//
//    public double getTotaalBedragInclusiefBtw() {
//        return totaalBedragInclusiefBtw;
//    }
//
//    public void setTotaalBedragInclusiefBtw(double totaalBedragInclusiefBtw) {
//        this.totaalBedragInclusiefBtw = totaalBedragInclusiefBtw;
//    }
//
//    public double getKeuringBedrag() {
//        return keuringBedrag;
//    }
//
//    public void setKeuringBedrag(double keuringBedrag) {
//        this.keuringBedrag = keuringBedrag;
//    }
//
//    public double getHandelingenBedrag() {
//        return handelingenBedrag;
//    }
//
//    public void setHandelingenBedrag(double handelingenBedrag) {
//        this.handelingenBedrag = handelingenBedrag;
//    }
//
//    public double getOnderdelenBedrag() {
//        return onderdelenBedrag;
//    }
//
//    public void setOnderdelenBedrag(double onderdelenBedrag) {
//        this.onderdelenBedrag = onderdelenBedrag;
//    }
//}
//




package nl.novi.garageapi.dto;

public class BonDto {
    private Long id;
    private double bedrag;
    private double totaalBedragInclusiefBtw;
    private double keuringBedrag;
    private double handelingenBedrag;
    private double onderdelenBedrag;

    // No-args constructor
    public BonDto() {
    }

    // Constructor with id and bedrag parameters
    public BonDto(Long id, double bedrag) {
        this.id = id;
        this.bedrag = bedrag;
    }

    // Full constructor with all fields
    public BonDto(Long id, double bedrag, double totaalBedragInclusiefBtw,
                  double keuringBedrag, double handelingenBedrag, double onderdelenBedrag) {
        this.id = id;
        this.bedrag = bedrag;
        this.totaalBedragInclusiefBtw = totaalBedragInclusiefBtw;
        this.keuringBedrag = keuringBedrag;
        this.handelingenBedrag = handelingenBedrag;
        this.onderdelenBedrag = onderdelenBedrag;
    }

    // Getters and Setters
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

    public double getTotaalBedragInclusiefBtw() {
        return totaalBedragInclusiefBtw;
    }

    public void setTotaalBedragInclusiefBtw(double totaalBedragInclusiefBtw) {
        this.totaalBedragInclusiefBtw = totaalBedragInclusiefBtw;
    }

    public double getKeuringBedrag() {
        return keuringBedrag;
    }

    public void setKeuringBedrag(double keuringBedrag) {
        this.keuringBedrag = keuringBedrag;
    }

    public double getHandelingenBedrag() {
        return handelingenBedrag;
    }

    public void setHandelingenBedrag(double handelingenBedrag) {
        this.handelingenBedrag = handelingenBedrag;
    }

    public double getOnderdelenBedrag() {
        return onderdelenBedrag;
    }

    public void setOnderdelenBedrag(double onderdelenBedrag) {
        this.onderdelenBedrag = onderdelenBedrag;
    }
}

