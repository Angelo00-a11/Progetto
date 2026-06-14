package Entity;

import jakarta.persistence.*;

@Entity
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIndirizzo")
    private Long id;

    @Column(name = "via")
    private String via;

    @Column(name = "numeroCivico")
    private String numeroCivico;

    @Column(name = "citta")
    private String citta;

    @Column(name = "cap")
    private int CAP;

    // Costruttore senza parametri (richiesto da JPA)
    public Indirizzo() {
    }

    public Indirizzo(String via, String civico, int cap, String citta) {
        this.via = via;
        this.numeroCivico = civico;
        this.CAP = cap;
        this.citta = citta;
    }

    public Long getId() {return id;}

    public String getVia() {
        return via;
    }
    public String getCivico() {
        return numeroCivico;
    }
    public String getCitta() { return citta; }
    public int getCap() { return CAP; }

    public void setVia(String via) {this.via = via;}
    public void setNumeroCivico(String numeroCivico) {this.numeroCivico = numeroCivico;}
    public void setCitta(String citta) {this.citta = citta;}
    public void setCAP(int CAP) {this.CAP = CAP;}

    @Override
    public String toString() {
        return via + " " + numeroCivico + ", " + CAP + ", " + citta;
    }

}
