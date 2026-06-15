package Entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordini")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idOrdine")
    private Long id;

    @Column(name = "stato", nullable = false)
    private String statoOrdine;

    // Ordine è il lato proprietario, Notifica non ha un campo Ordine
    @OneToOne(cascade = CascadeType.ALL) // Aggiunto cascade per persistenza/aggiornamento
    @JoinColumn(name = "idNotifica", // Nome della colonna FK nella tabella 'ordini'
                referencedColumnName = "idNotifica", // Riferimento alla proprietà 'id' in Notifica
                nullable = true) // Una notifica potrebbe non essere sempre presente
    private Notifica notifica;

    // CORREZIONE 2: Relazione Ordine -> Indirizzo

    // Cambiato @OneToOne in @ManyToOne (un indirizzo può essere usato da più ordini)
    @OneToOne(cascade = CascadeType.PERSIST) // Aggiunto cascade per persistenza di nuovi indirizzi
    @JoinColumn(name = "idIndirizzoConsegna", // Nome della colonna FK nella tabella 'ordini'
                referencedColumnName = "idIndirizzo", // Riferimento alla proprietà 'id' in Indirizzo
                nullable = false) // Un ordine deve avere un indirizzo di consegna
    private Indirizzo indirizzoConsegna;

    /*  - name: Specifica il nome della colonna nel database che sarà la Foreign Key nella tabella dell'entità corrente (Ordine). In questo caso, sarà una colonna cliente_id nella tabella ordini.
        - referencedColumnName: Specifica il nome della colonna nella tabella dell'entità referenziata (Cliente) a cui la Foreign Key si riferisce. Di solito è la Primary Key (id) dell'entità referenziata.
        - nullable: Se impostato a false, significa che la Foreign Key non può essere NULL. Ogni Ordine deve essere associato a un Cliente.
    Rappresentazione DB: Una singola colonna Foreign Key (idNotifica) viene aggiunta alla tabella ordini (o notifiche, a seconda di quale entità è il lato proprietario).
    */
    @ManyToOne
    @JoinColumn(name = "IdCliente",
            referencedColumnName = "id", // Riferimento alla colonna PK in Utente/Cliente
            nullable = false)
    private Cliente cliente;

    /*  - mappedBy = (Si usa solo la navigabiltà è bidirezionale) "ordine" è posta sul lato inverso della relazione (il lato "uno", che non contiene la Foreign Key). Dice a JPA che la relazione è gestita dal campo ordine (che è un @ManyToOne) nell'entità RigaCarrelloVirtuale.
        - cascade = CascadeType.ALL: Indica che le operazioni di persistenza (salvataggio, aggiornamento, eliminazione) eseguite sull'entità Ordine devono essere propagate anche alle entità RigaCarrelloVirtuale associate.
        - fetch = FetchType.LAZY: Indica che le RigaCarrelloVirtuale associate non devono essere caricate dal database immediatamente quando viene caricato l'Ordine, ma solo quando vengono effettivamente richieste.
        Rappresentazione DB: La Foreign Key (idOrdine) si trova nella tabella rigaCarrelloVirtuale (il lato "molti").
        */

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RigaCarrelloVirtuale> carrello;

    // Costruttore senza parametri (richiesto da JPA)
    public Ordine() {
    }

    public Ordine(String stato, Indirizzo indirizzoConsegna, Cliente cliente) {
        this.statoOrdine = stato;
        this.indirizzoConsegna = indirizzoConsegna;
        this.cliente = cliente;
        this.carrello = new ArrayList<>();
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getStatoOrdine() {return statoOrdine;}
    public void setStatoOrdine(String statoOrdine) {this.statoOrdine = statoOrdine;}

    public Notifica getNotifica() {return notifica;}
    public void setNotifica(Notifica notifica) {this.notifica = notifica;}

    public Indirizzo getIndirizzoConsegna() {return indirizzoConsegna;}
    public void setIndirizzoConsegna(Indirizzo indirizzoConsegna) {this.indirizzoConsegna = indirizzoConsegna;}

    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    //non dev'essere possibile cambiare il cliente che ha eseguito l'ordine
    //eppure mi serve nel gestore ordini

    public List<RigaCarrelloVirtuale> getCarrello() {return carrello;}
    public void setCarrello(List<RigaCarrelloVirtuale> carrello) {this.carrello = carrello;}

    public void addPiattoAlCarrello(Piatto p, int quantita) {
        RigaCarrelloVirtuale rigaCarrello = new RigaCarrelloVirtuale(p, quantita);
        rigaCarrello.setOrdine(this);

        if (carrello == null) {
            carrello = new ArrayList<>();
        }

        // Controlla se il piatto è già nel carrello
        for (RigaCarrelloVirtuale riga : carrello) {
            if (riga.getPiatto().getId().equals(p.getId())) {
                // Se il piatto è già nel carrello, aumenta solo la quantità
                riga.setQuantita(riga.getQuantita() + quantita);
                return;
            }
        }

        // Altrimenti, aggiungi la nuova riga
        carrello.add(rigaCarrello);
    }

    public float getTotale() {
        float totale = 0;
        if (carrello != null) {
            for (RigaCarrelloVirtuale riga : carrello) {
                totale += riga.getPiatto().getPrezzo() * riga.getQuantita();
            }
        }
        return totale;
    }
}
