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
    private int numeroCivico;

    @Column(name = "citta")
    private String citta;

    @Column(name = "cap")
    private int CAP;

    // Costruttore senza parametri (richiesto da JPA)
    public Indirizzo() {
    }

    public Indirizzo(String via, int civico, String citta, int cap) {
        this.via = via;
        this.numeroCivico = civico;
        this.citta = citta;
        this.CAP = cap;
    }

    public String getVia() {
        return via;
    }
    public int getCivico() {
        return numeroCivico;
    }
    public String getCitta() { return citta; }
    public int getCap() { return CAP; }

    public void setVia(String via) {this.via = via;}
    public void setNumeroCivico(int numeroCivico) {this.numeroCivico = numeroCivico;}
    public void setCitta(String citta) {this.citta = citta;}
    public void setCAP(int CAP) {this.CAP = CAP;}

    @Override
    public String toString() {
        return via + " " + numeroCivico + ", " + CAP + ", " + citta;
    }

}
