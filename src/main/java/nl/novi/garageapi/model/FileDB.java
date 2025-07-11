package nl.novi.garageapi.model;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class FileDB {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String name;
    private String type;

    @Lob
    private byte[] data;

    @ManyToOne
    @JoinTable(name = "kassamedewerker_id")
    private KassaMedewerker kassaMedewerker;

    public FileDB() {

    }
    public FileDB(String name, String type, byte[] data, KassaMedewerker kassaMedewerker) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.kassaMedewerker = kassaMedewerker;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public KassaMedewerker getKassaMedewerker() {
        return kassaMedewerker;
    }

    public void setKassaMedewerker(KassaMedewerker kassaMedewerker) {
        this.kassaMedewerker = kassaMedewerker;
    }
}