package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notifiche")
public class Notifica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNotifica")
    private Long id;

    @Column(name = "testoNotifica")
    private String testoNotifica;

    @Column(name = "statoNotifica")
    private String statoNotifica;

    // Costruttore senza parametri (richiesto da JPA)
    public Notifica() {
    }

    public Notifica(String testoNotifica, String statoNotifica) {
        this.testoNotifica = testoNotifica;
        this.statoNotifica = statoNotifica;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTestoNotifica() {
        return testoNotifica;
    }
    public void setTestoNotifica(String testoNotifica) {
        this.testoNotifica = testoNotifica;
    }
    public String getStatoNotifica() {
        return statoNotifica;
    }
    public void setStatoNotifica(String statoNotifica) {
        this.statoNotifica = statoNotifica;
    }
}


