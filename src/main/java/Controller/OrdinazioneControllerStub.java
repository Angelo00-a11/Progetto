package Controller;

import Database.Session;
import Entity.*;

import java.util.ArrayList;
import java.util.List;

public class OrdinazioneControllerStub {

    public static boolean salvaOrdinazione(ArrayList<String> piatti, ArrayList<Integer> quantita, String via, String civico, String cap, String citta) {
        try {
            Cliente cliente = (Cliente) Session.getInstance().getUtenteLoggato();
            if (cliente == null) {
                System.out.println("Errore: nessun cliente loggato");
                return false;
            }

            Indirizzo indirizzoConsegna = new Indirizzo(via, civico, Integer.parseInt(cap), citta);
            GestoreOrdini go = new GestoreOrdini();

            // Costruisce il carrello
            List<RigaCarrelloVirtuale> carrello = new ArrayList<>();
            for (int i = 0; i < piatti.size(); i++) {
                if (quantita.get(i) > 0) {
                    Piatto piatto = go.cercaOCreaPiatto(piatti.get(i));
                    if (piatto == null) {
                        System.out.println("Errore: piatto '" + piatti.get(i) + "' non trovato");
                        return false;
                    }
                    RigaCarrelloVirtuale riga = new RigaCarrelloVirtuale(piatto, quantita.get(i));
                    carrello.add(riga);
                }
            }

            // Delega tutto al GestoreOrdini
            Ordine ordine = go.registraOrdine(cliente, indirizzoConsegna, carrello);
            if (ordine != null) {
                System.out.println("Ordinazione salvata con successo");
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio dell'ordinazione: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
