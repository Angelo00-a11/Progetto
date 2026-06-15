package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("AMMINISTRATORE")
public class Amministratore extends Utente {
/*      NON MI SERVONO COME ATTRIBUTI, uso List<Ristorante> come variabile locale in monitoraSistema
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "amministratore_ristorante",
        joinColumns = @JoinColumn(name = "idAmministratore"),
        inverseJoinColumns = @JoinColumn(name = "id_Ristorante")
    )
    private List<Ristorante> ristorantiConsiderati;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "amministratore_ordine",
        joinColumns = @JoinColumn(name = "idAmministratore"),
        inverseJoinColumns = @JoinColumn(name = "idOrdine")
    )
    private List<Ordine> ordiniConsiderati;
*/

    // Costruttore senza parametri (richiesto da JPA)
    public Amministratore() {
        super();
    }

    public Amministratore(String nome, String cognome, String email, String ruolo, String via, String civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via, civico, cap, citta);
    }

/*      NON SERVE
    public List<Ristorante> getRistorantiConsiderati() {
        return ristorantiConsiderati;
    }
    public void setRistorantiConsiderati(List<Ristorante> ristorantiConsiderati) {
        this.ristorantiConsiderati = ristorantiConsiderati;
    }
    public List<Ordine> getOrdiniConsiderati() {
        return ordiniConsiderati;
    }
    public void setOrdiniConsiderati(List<Ordine> ordiniConsiderati) {
        this.ordiniConsiderati = ordiniConsiderati;
    }
*/

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


    // ********************************
    //  METODO PUBLIC MONITORA SISTEMA
    // ********************************
    public Map<String, Object> monitoraSistema(LocalDate dataInizio, LocalDate dataFine){

        //recupera la lista di ristoranti da considerare
        List<Ristorante> ristoranti = this.recpueraRistoranti(dataInizio, dataFine);

        //calcola le statistiche
        int totOrdini = this.calcoloTotaleOrdini(ristoranti, dataInizio, dataFine);
        double volOrdini = this.calcoloVolumeMedioOrdini(ristoranti, dataInizio, dataFine);
        List<Object[]> topRistoranti = this.calcoloRistorantiPiuAttivi(ristoranti, dataInizio, dataFine);

        //impacchetta e restituisce i risultati
        Map<String, Object> statistiche = new HashMap<>();
        statistiche.put("totaleOrdini", totOrdini);
        statistiche.put("volumeOrdini", volOrdini);
        statistiche.put("ristPiuAttivi", topRistoranti);

        return statistiche;
    }


    // ****************************
    //  METODI PRIVATI DI UTILITA'
    // ****************************

    //INPUT: intervallo di date - OUTPUT: lista di ristoranti che hanno evaso almeno un ordine nel periodo
    private List<Ristorante> recpueraRistoranti(LocalDate dataInizio, LocalDate dataFine) {
        // da implementare --> vedi come interagire con DB --> INIZIA A LAVORARE SUL CONTROLLER !
    }

    //INPUT: lista ristoranti e intervallo di date - OUTPUT: totale ordini nel periodo
    private int calcoloTotaleOrdini(List<Ristorante> ristoranti, LocalDate dataInizio, LocalDate dataFine) {
        int totale = 0;
        for(Ristorante r : ristoranti){
            totale += this.recuperaOrdini(r, dataInizio, dataFine).size();
        }

        return totale;
    }

    //INPUT: lista ristoranti e intervallo di date - OUTPUT: volume medio ordini nel periodo
    // NOTA: la List<Ristorante> contiene solo ristoranti che hanno evaso almeno un ordine nel periodo !
    private double calcoloVolumeMedioOrdini(List<Ristorante> ristoranti, LocalDate dataInizio, LocalDate dataFine){
        double totale = 0;
        int numOrdini = 0;

        for(Ristorante r : ristoranti){
            for(Ordine o : this.recuperaOrdini(r, dataInizio, dataFine)) {
                totale += o.getTotale();
                numOrdini ++;
            }
        }

        if(totale == 0.0 || numOrdini == 0){
            return 0.0;
        }
        else{
            return totale/numOrdini;
        }
    }

    //INPUT: lista ristoranti e intervallo di date - OUTPUT: lista di ristoranti + nome ristoratore + ordini evasi nel periodo
    private List<Object[]> calcoloRistorantiPiuAttivi(List<Ristorante> ristoranti, LocalDate dataInizio, LocalDate dataFine){
        List<Object[]> risultato = new ArrayList<>();

        for(Ristorante r : ristoranti){
            String nomeRistorante = r.getNome();
            int numOrdini = this.recuperaOrdini(r, dataInizio, dataFine).size();
            String proprietario = r.getRistoratoreResponsabile().getNome() + " " + r.getRistoratoreResponsabile().getCognome();
            risultato.add(new Object[]{nomeRistorante, proprietario, numOrdini});
        }
        //ordinamento (decrescente) in base al numOrdini
        risultato.sort( (a, b) -> Integer.compare((int)b[2], (int)a[2]) );

        return risultato;
    }

    //INPUT: ristorante e intervallo di date - OUTPUT: lista di ordini nel periodo (del singolo ristorante)
    private List<Ordine> recuperaOrdini(Ristorante ristorante, LocalDate dataInizio, LocalDate dataFine){
        List<Ordine> daConsiderare = new ArrayList<>();
        for(Ordine o : ristorante.getOrdiniRicevuti()){
            LocalDate dataOrdine = o.getData();     //NOTA: VEDERE COME RISOLVERE IL PROBLEMA / IMPLEMENTARE GET
            boolean nelPeriodo = !dataOrdine.isAfter(dataInizio) && !dataOrdine.isBefore(dataFine);
            boolean evaso = o.getStatoOrdine().equals("evaso");      //NOTA: VERIFICARE STRINGA ESATTA PER STATO EVASO
            if (nelPeriodo && evaso){
                daConsiderare.add(o);
            }
        }

        return daConsiderare;
    }

}

/*
 ********** LA NOTA LA LASCIO O LA CANCELLO? **********
*/

// NOTA ARCHITETTURALE — Consistenza dei dati durante il monitoraggio
// In un sistema multi-thread o multi-utente, i tre calcoli successivi al monitoraSistema (totaleOrdini, volumeMedio,
// ristorantiPiuAttivi) potrebbero operare su dati non consistenti se nuovi ordini vengono inseriti tra una chiamata
// e l'altra.
// Possibili soluzioni:
// 1. Deep copy immutabile della lista ristoranti+ordini prima dei calcoli
// 2. Blocco synchronized sulla risorsa condivisa durante l'intera esecuzione
// 3. Esecuzione di tutti i calcoli in un'unica transazione a livello DB
// In questo contesto single-threaded il rischio non si concretizza e non viene implementato alcun meccanismo