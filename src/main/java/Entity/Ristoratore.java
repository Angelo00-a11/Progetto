package Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("RISTORATORE")
public class Ristoratore extends Utente {

    @OneToMany(mappedBy = "ristoratoreResponsabile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ristorante> ristorantiGestiti;

    // Costruttore senza parametri (richiesto da JPA)
    public Ristoratore() {
        super();
    }

    public Ristoratore(String nome, String cognome, String email, String ruolo, String via, String civico, int cap, String citta) {
        super(nome, cognome, email, ruolo, via, civico, cap,citta);
        this.ristorantiGestiti = new ArrayList<>();
    }

    public List<Ristorante> getRistorantiGestiti() {
        return ristorantiGestiti;
    }

    public void setRistorantiGestiti(List<Ristorante> ristorantiGestiti) {
        this.ristorantiGestiti = ristorantiGestiti;
    }

    @Override
    public int Accedi(String nome, String cognome, String email) {
        int res;
        if (this.getNome().equals(nome) && this.getCognome().equals(cognome) && this.getEmail().equals(email)) {
            res = 0;
        } else {res = -1;}
        return res;
    }

    public void addRistorante(Ristorante r) {
        if (ristorantiGestiti == null) {
            ristorantiGestiti = new ArrayList<>();
        }
        ristorantiGestiti.add(r);
    }

    public void removeRistorante(Ristorante r) {
        if (ristorantiGestiti != null) {
            ristorantiGestiti.remove(r);
        }
    }

    public Ristorante creaRistorante(String nome, String descrizione, String via, String civico, String citta, int cap, String giorno, int inizioServizio, int fineServizio) {
        return new Ristorante(nome, descrizione, via, civico, citta, cap, giorno, inizioServizio, fineServizio, this);
    }

}