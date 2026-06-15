package Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue("AMMINISTRATORE")
public class Amministratore extends Utente {

    // Costruttore senza parametri (richiesto da JPA)
    public Amministratore() {
        super();
    }

    public Amministratore(String nome, String cognome, String email, String ruolo, String via, String civico, String citta, int cap) {
        super(nome, cognome, email, ruolo, via, civico, cap, citta);
    }

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
    public Map<String, Object> monitoraSistema(List<Ristorante> ristoranti, LocalDate dataInizio, LocalDate dataFine){

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
    // NOTA: il metodo viene chiamato più volte per i calcoli statistici (problema non rilevante nel contesto attuale)
    //       una possibile ottimizzazione è una cache in monitoraSistema del tipo Map<Ristorante, List<Ordine>> ordiniFiltrati
    private List<Ordine> recuperaOrdini(Ristorante ristorante, LocalDate dataInizio, LocalDate dataFine){
        List<Ordine> daConsiderare = new ArrayList<>();
        for(Ordine o : ristorante.getOrdiniRicevuti()){
            LocalDate dataOrdine = o.getData();
            boolean nelPeriodo = !dataOrdine.isBefore(dataInizio) && !dataOrdine.isAfter(dataFine);
            boolean evaso = "evaso".equalsIgnoreCase(o.getStatoOrdine());
            if (nelPeriodo && evaso){
                daConsiderare.add(o);
            }
        }

        return daConsiderare;
    }

}


// NOTA ARCHITETTURALE — Consistenza dei dati durante il monitoraggio
// In un sistema multi-thread o multi-utente, i tre calcoli successivi al monitoraSistema (totaleOrdini, volumeMedio,
// ristorantiPiuAttivi) potrebbero operare su dati non consistenti se nuovi ordini vengono inseriti tra una chiamata
// e l'altra.
// Possibili soluzioni:
// 1. Deep copy immutabile della lista ristoranti+ordini prima dei calcoli
// 2. Blocco synchronized sulla risorsa condivisa durante l'intera esecuzione
// 3. Esecuzione di tutti i calcoli in un'unica transazione a livello DB
// In questo contesto single-threaded il rischio non si concretizza e non viene implementato alcun meccanismo