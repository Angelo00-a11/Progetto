package Controller;

import Entity.GestoreOrdini;
import Entity.Notifica;
import Entity.Ordine;

import java.util.List;

/**
 CONTROLLER PER IL CASO D'USO GESTISCI ORDINE
 */
public class GestisciOrdineController {

    public static List<Ordine> getOrdiniInviati() {
        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        return gestoreOrdini.cercaOrdiniInStato("inviato");
    }

    // Viene chiamato dal bottone "ACCETTA" del FormDettagliOrdine
    //a questo metodo viene passato l'ordine del database
    public static boolean accettaOrdine(Ordine ordine) {
        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        if (ordine == null || !ordine.getStatoOrdine().equalsIgnoreCase("inviato")) return false;

        // Modifica l'oggetto in memoria
        Ordine aggiornato = gestoreOrdini.aggiornaOrdine("in preparazione", ordine.getIndirizzoConsegna(), ordine.getId());

        // Simula l'invio della notifica
        if(aggiornato != null) {
            inviaNotifica(aggiornato, "L'ordine è in preparazione!");

            //aggiorno l'ordine anche in memoria ram
            ordine.setStatoOrdine("in preparazione");

            return true;
        }

        return false;
    }

    // Viene chiamato dal FormAggiornaStatoOrdine
    public static boolean segnalaOrdinePronto(Ordine ordine) {
        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        if (ordine == null || !ordine.getStatoOrdine().equalsIgnoreCase("in preparazione")) return false;

        Ordine aggiornato = gestoreOrdini.aggiornaOrdine("in consegna", ordine.getIndirizzoConsegna(), ordine.getId());

        if(aggiornato != null) {
            inviaNotifica(ordine, "L'ordine è in consegna!");
            return true;
        }

        return false;
    }

    // Viene chiamato dal bottone "RIFIUTA" del FormDettagliOrdine
    public static boolean rifiutaOrdine(Ordine ordine) {
        GestoreOrdini gestoreOrdini = new GestoreOrdini();
        if (ordine == null || !ordine.getStatoOrdine().equalsIgnoreCase("inviato")) return false;

        Ordine aggiornato = gestoreOrdini.aggiornaOrdine("rifiutato", ordine.getIndirizzoConsegna(), ordine.getId());

        if(aggiornato != null) {
            inviaNotifica(ordine, "L'ordine è stato rifiutato.");
            return true;
        }

        return false;
    }

    //metodo privato per le notifiche

    private static void inviaNotifica(Ordine ordine, String testo) {
        Notifica notifica = new Notifica(testo, "Non letta");
        ordine.setNotifica(notifica);

        // Stampiamo in console per far vedere che il controller sta funzionando!
        System.out.println("--> [SIMULAZIONE NOTIFICA] Per Ordine ID " + ordine.getId() + ": " + testo);
    }
}
