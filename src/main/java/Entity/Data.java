package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "date")
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idData")
    private Long id;

    @Column(name = "giorno", nullable = false)
    private String giorno;

    @Column(name = "inizioServizio", nullable = false)
    private int inizioServizio;

    @Column(name = "fineServizio", nullable = false)
    private int fineServizio;

    // Costruttore senza parametri (richiesto da JPA)
    public Data() {
    }

    public Data(String giorno, int inizioServizio, int fineServizio) {
        this.giorno = giorno;
        this.inizioServizio = inizioServizio;
        this.fineServizio = fineServizio;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getGiorno() {
        return giorno;
    }
    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }
    public int getInizioServizio() {
        return inizioServizio;
    }
    public int getFineServizio() {
        return fineServizio;
    }
    public void setInizioServizio(int inizioServizio) {
        this.inizioServizio = inizioServizio;
    }
    public void setFineServizio(int fineServizio) {
        this.fineServizio = fineServizio;
    }

}

