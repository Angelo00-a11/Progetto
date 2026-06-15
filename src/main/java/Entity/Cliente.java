package Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Utente {

    // Rimosso il campo @OneToOne Ordine ordine; per evitare ambiguità con ordiniEffettuati
    // @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private Ordine ordine;

    // CORREZIONE: Relazione Cliente -> Ordine (lista ordiniEffettuati)
    // Usa mappedBy per indicare che la relazione è gestita dal campo 'cliente' in Entity.Ordine
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // RIPRISTINATO QUI
    private List<Ordine> ordiniEffettuati;

    /*  - @JoinTable: Questa annotazione è obbligatoria per le relazioni @ManyToMany e definisce la tabella di join intermedia che gestisce la relazione nel database.
        - name = "idCliente": Specifica il nome della tabella di join nel database. (Il nome idCliente è fuorviante qui, dovrebbe essere qualcosa come cliente_ristorante o clienti_ristoranti).
        - joinColumns = @JoinColumn(name = "idCliente"): Definisce la Foreign Key nella tabella di join che punta alla Primary Key dell'entità corrente (Cliente).
        - inverseJoinColumns = @JoinColumn(name = "idRistorante"): Definisce la Foreign Key nella tabella di join che punta alla Primary Key dell'entità associata (Ristorante).
        Rappresentazione DB: Viene creata una tabella intermedia (cliente_ristorante o simile) che contiene due Foreign Key, una per Cliente e una per Ristorante, formando la relazione molti-a-molti.
    */
   /* @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "idCliente",
        joinColumns = @JoinColumn(name = "idCliente"),
        inverseJoinColumns = @JoinColumn(name = "idRistorante")
    )
    private List<Ristorante> ristorantiDisponibili;


    LEVATO PERCHE' POI ABBIAMO INTRODOTTO GESTORERISTORANTI
    */

    // Costruttore senza parametri (richiesto da JPA)
    public Cliente() {
        super();
    }

    public Cliente(String nome, String cognome, String email, String ruolo, String via, String civico, int cap,String citta) {
        super(nome, cognome, email, ruolo, via, civico, cap, citta);
        this.ordiniEffettuati = new ArrayList<>();
        //this.ristorantiDisponibili = new ArrayList<>();
    }

    public List<Ordine> getOrdiniEffettuati() {return ordiniEffettuati;}
    public void setOrdiniEffettuati(List<Ordine> ordiniEffettuati) {this.ordiniEffettuati = ordiniEffettuati;}
    public void addOrdineEffettuato(Ordine o){

        if(ordiniEffettuati==null) {
            this.ordiniEffettuati = new ArrayList<>();
        }else{
            this.ordiniEffettuati.add(o);
        }
    }

    /*
    public List<Ristorante> getRistorantiDisponibili() {
        return ristorantiDisponibili;
    }
    public void setRistorantiDisponibili(List<Ristorante> ristorantiDisponibili) {
        this.ristorantiDisponibili = ristorantiDisponibili;
    }*/

    @Override
    public int Accedi(String nome, String cognome, String email) {
        int res;
        if (this.getNome().equals(nome) && this.getCognome().equals(cognome) && this.getEmail().equals(email)) {
            res = 0;
        } else {
            res = -1;
        }
        return res;
    }

    public Ordine creaOrdine(ArrayList<RigaCarrelloVirtuale> carrello, Indirizzo indirizzoConsegna) {
        //di default l'ordine viene creato con indirizzoConsegna, indirizzo (quello con cui si è registrato il Cliente)
        Ordine o = new Ordine("Creato", this.getIndirizzo(), this);
        o.setCarrello(carrello);

        if(!indirizzoConsegna.equals(this.getIndirizzo())) {
            o.setIndirizzoConsegna(indirizzoConsegna);
        }

        return o;
    }

    public void Ordina(Ordine o) {

        o.setStatoOrdine("inviato");
        // Associa il cliente all'ordine
        o.setCliente(this);

        if (this.ordiniEffettuati == null) {
            this.ordiniEffettuati = new ArrayList<>();
        }
        this.addOrdineEffettuato(o);
    }

}