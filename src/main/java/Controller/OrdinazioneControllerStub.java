package Controller;

import Database.GestorePersistenza;
import Database.Session;
import Entity.*;

import java.util.ArrayList;
import java.util.Map;

public class OrdinazioneControllerStub {


    /**
     * Salva un'ordinazione nel database.
     *
     * @param piatti Lista dei nomi dei piatti ordinati
     * @param quantita Lista delle quantità corrispondenti
     * @param indirizzoConsegna Indirizzo di consegna (formato: "via civico, CAP, città")
     * @return true se l'ordinazione è stata salvata con successo, false altrimenti
     */
    public static boolean salvaOrdinazione(ArrayList<String> piatti, ArrayList<Integer> quantita, String indirizzoConsegna) {

        try {
            // Recuperare il Cliente dalla sessione
            Session session = Session.getInstance();
            Cliente cliente = (Cliente) session.getUtenteLoggato();

            if (cliente == null) {
                System.out.println("Errore: nessun cliente loggato");
                return false;
            }

            // Creare un nuovo ordine
            Indirizzo indirizzoConsegnaObj = StringToIndirizzo(indirizzoConsegna);
            Ordine ordine = new Ordine("Creato", indirizzoConsegnaObj, cliente);

            GestorePersistenza gp = new GestorePersistenza();//##########NON SO SE é GIUSTO QUI######################
            // Aggiungere i piatti al carrello
            for (int i = 0; i < piatti.size(); i++) {
                if (quantita.get(i) > 0) {
                    // Cercare il piatto nel database per nome
                    Piatto piatto = gp.cercaPrimoPerCampi(Piatto.class, Map.of("nomePiatto", piatti.get(i)));

                    if (piatto == null) {
                        gp.salva(new Piatto(piatti.get(i), "Descrizione di " + piatti.get(i), 10));
                        piatto = gp.cercaPrimoPerCampi(Piatto.class, Map.of("nomePiatto", piatti.get(i)));
                    }

                   if (piatto == null) {
                        System.out.println("Errore: piatto '" + piatti.get(i) + "' non trovato nel database");
                        return false;
                    }

                    ordine.addPiattoAlCarrello(piatto, quantita.get(i));
                }
            }

            // Salvare l'ordine nel database
            boolean risultato = gp.salva(ordine);

            if (risultato) {
                System.out.println("Ordinazione salvata con successo");
                // Aggiornare lo stato dell'ordine
                ordine.setStatoOrdine("inviato");
                cliente.Ordina(ordine);
            }

            return risultato;

        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio dell'ordinazione: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private static Indirizzo StringToIndirizzo(String indirizzoConsegna) {
         String[] parts = indirizzoConsegna.split(" ");
         String via = parts[0].trim();
         String civico = parts[1].trim();
         String cap = parts[2].trim();
         String citta = parts[3].trim();
            return new Indirizzo(via, Integer.parseInt(civico), citta, Integer.parseInt(cap));
    }

}
